import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import api from '../api';
import './Payment.css';

const BOOKING_SERVICE_URL = 'http://172.16.56.102:8083';
const readFirst = (...values) => values.find((value) => value !== undefined && value !== null && value !== '');

const normalizeBookingPayload = (payload) => {
  if (!payload || typeof payload !== 'object') return null;

  if (Array.isArray(payload)) {
    return payload[0] || null;
  }

  if (payload.data && typeof payload.data === 'object' && !Array.isArray(payload.data)) {
    return payload.data;
  }

  if (payload.booking && typeof payload.booking === 'object') {
    return payload.booking;
  }

  return payload;
};

const normalizeBookingListPayload = (payload) => {
  if (Array.isArray(payload)) return payload;
  if (Array.isArray(payload?.data)) return payload.data;
  if (Array.isArray(payload?.bookings)) return payload.bookings;
  if (Array.isArray(payload?.data?.bookings)) return payload.data.bookings;
  return [];
};

const getBookingId = (booking) =>
  readFirst(booking?.bookingId, booking?._id, booking?.id, booking?.data?.bookingId);

const getMovieId = (booking, stateMovieId) =>
  readFirst(
    stateMovieId,
    booking?.movieId,
    booking?.movie?._id,
    booking?.movie?.id,
    booking?.data?.movieId
  );

const getPaymentUrl = (booking) =>
  readFirst(
    booking?.paymentUrl,
    booking?.payment_url,
    booking?.checkoutUrl,
    booking?.checkout_url,
    booking?.payUrl,
    booking?.payment?.url,
    booking?.data?.paymentUrl
  );

const getAmount = (booking) =>
  readFirst(
    booking?.totalAmount,
    booking?.amount,
    booking?.total,
    booking?.price,
    booking?.data?.totalAmount
  );

const formatAmount = (amount) => {
  if (typeof amount !== 'number') return null;
  return `${new Intl.NumberFormat('vi-VN').format(amount)} VND`;
};

const getUserIdFromStorage = () => {
  try {
    const user = JSON.parse(localStorage.getItem('user') || 'null');
    return user?.id || user?._id || user?.userId || null;
  } catch {
    return null;
  }
};

const getStatusInfo = (booking) => {
  const rawStatus = String(
    readFirst(
      booking?.paymentStatus,
      booking?.payment?.status,
      booking?.status,
      booking?.paymentResult,
      booking?.payment_state,
      'processing'
    )
  ).toLowerCase();

  const rawMessage = String(
    readFirst(booking?.message, booking?.paymentMessage, booking?.payment?.message, '')
  ).toLowerCase();

  if (
    ['paid', 'success', 'completed', 'done', 'succeeded', 'confirmed'].includes(rawStatus) ||
    rawMessage.includes('thanh cong') ||
    rawMessage.includes('success')
  ) {
    return {
      className: 'is-success',
      text: 'Đã thanh toán'
    };
  }

  if (
    ['failed', 'cancelled', 'canceled', 'error', 'rejected'].includes(rawStatus) ||
    rawMessage.includes('that bai') ||
    rawMessage.includes('failed')
  ) {
    return {
      className: 'is-failed',
      text: 'Thanh toán thất bại'
    };
  }

  return {
    className: 'is-processing',
    text: 'Đang xử lý thanh toán'
  };
};

const fetchBookingById = async (bookingId) => {
  if (!bookingId) return null;

  const endpointCandidates = [
    `${BOOKING_SERVICE_URL}/bookings/${bookingId}`,
    `${BOOKING_SERVICE_URL}/bookings/${bookingId}/status`,
    `${BOOKING_SERVICE_URL}/bookings/status/${bookingId}`
  ];

  let lastError = null;

  for (const endpoint of endpointCandidates) {
    try {
      const response = await api.get(endpoint);
      const normalized = normalizeBookingPayload(response.data);
      if (normalized) return normalized;
    } catch (error) {
      lastError = error;
      if (error.response?.status === 404) {
        continue;
      }
      throw error;
    }
  }

  if (lastError?.response?.status === 404) {
    return null;
  }

  throw lastError;
};

const fetchBookingFromHistoryById = async (userId, bookingId) => {
  if (!userId || !bookingId) return null;

  const endpointCandidates = [
    `${BOOKING_SERVICE_URL}/bookings/user/${userId}`,
    `${BOOKING_SERVICE_URL}/bookings/history/${userId}`,
    `${BOOKING_SERVICE_URL}/bookings?userId=${encodeURIComponent(userId)}`
  ];

  for (const endpoint of endpointCandidates) {
    try {
      const response = await api.get(endpoint);
      const history = normalizeBookingListPayload(response.data);
      const matchedBooking = history.find(
        (item) => String(getBookingId(item)) === String(bookingId)
      );
      if (matchedBooking) {
        return matchedBooking;
      }
    } catch (error) {
      if (error.response?.status === 404) {
        continue;
      }
      throw error;
    }
  }

  return null;
};

function Payment() {
  const location = useLocation();
  const navigate = useNavigate();

  const initialBooking = location.state?.booking || {};
  const [liveBooking, setLiveBooking] = useState(initialBooking);
  const [isRefreshing, setIsRefreshing] = useState(false);
  const [lastCheckedAt, setLastCheckedAt] = useState(null);
  const [refreshError, setRefreshError] = useState('');
  const [isAutoRefreshEnabled, setIsAutoRefreshEnabled] = useState(true);

  const booking = liveBooking || initialBooking;

  const selectedSeats =
    (Array.isArray(booking?.seats) && booking.seats.length > 0
      ? booking.seats
      : location.state?.selectedSeats) || [];

  const bookingId = getBookingId(booking);
  const movieId = getMovieId(booking, location.state?.movieId);
  const paymentUrl = getPaymentUrl(booking);
  const totalAmount = readFirst(location.state?.totalAmount, getAmount(booking));
  const status = useMemo(() => getStatusInfo(booking), [booking]);

  const isTerminalStatus = status.className === 'is-success' || status.className === 'is-failed';

  const refreshPaymentStatus = useCallback(async () => {
    if (!bookingId) return;

    setIsRefreshing(true);
    setRefreshError('');

    try {
      const latestById = await fetchBookingById(bookingId);
      if (latestById) {
        setLiveBooking((prev) => ({ ...prev, ...latestById }));
        setLastCheckedAt(new Date());
        return;
      }

      const userId = getUserIdFromStorage();
      const latestFromHistory = await fetchBookingFromHistoryById(userId, bookingId);

      if (latestFromHistory) {
        setLiveBooking((prev) => ({ ...prev, ...latestFromHistory }));
        setLastCheckedAt(new Date());
        return;
      }

      setRefreshError('Không tìm thấy dữ liệu cập nhật cho đặt vé này từ backend.');
    } catch (error) {
      const statusCode = error.response?.status;
      const backendMessage =
        error.response?.data?.message ||
        (typeof error.response?.data === 'string' ? error.response.data : error.message);

      setRefreshError(
        `Không thể cập nhật trạng thái${statusCode ? ` (HTTP ${statusCode})` : ''}: ${backendMessage}`
      );
    } finally {
      setIsRefreshing(false);
    }
  }, [bookingId]);

  useEffect(() => {
    if (!bookingId || !isAutoRefreshEnabled || isTerminalStatus) return;

    const initialRefreshTimeout = setTimeout(() => {
      refreshPaymentStatus();
    }, 0);

    const intervalId = setInterval(refreshPaymentStatus, 5000);

    return () => {
      clearTimeout(initialRefreshTimeout);
      clearInterval(intervalId);
    };
  }, [bookingId, isAutoRefreshEnabled, isTerminalStatus, refreshPaymentStatus]);

  const liveMetaText = lastCheckedAt
    ? `Cập nhật lúc: ${lastCheckedAt.toLocaleTimeString('vi-VN')}`
    : 'Đang theo dõi cập nhật từ dịch vụ thanh toán...';

  return (
    <main className="payment-page">
      <div className="payment-aura payment-aura-left" />
      <div className="payment-aura payment-aura-right" />

      <section className="payment-card">
        <p className="payment-overline">Thanh toán</p>
        <h2>Thanh toán vé xem phim</h2>

        <div className="payment-status-wrap">
          <span className={`payment-status ${status.className}`}>{status.text}</span>
        </div>

        <p className="payment-live-meta">{liveMetaText}</p>
        {refreshError ? <p className="payment-refresh-error">{refreshError}</p> : null}

        <div className="payment-grid">
          <div className="payment-item">
            <span className="payment-item-label">Mã đặt vé</span>
            <span className="payment-item-value">{bookingId || 'Đang cập nhật'}</span>
          </div>

          <div className="payment-item">
            <span className="payment-item-label">Mã phim</span>
            <span className="payment-item-value">{movieId || 'Đang cập nhật'}</span>
          </div>

          <div className="payment-item">
            <span className="payment-item-label">Ghế đã chọn</span>
            <span className="payment-item-value">
              {selectedSeats.length > 0 ? selectedSeats.join(', ') : 'Chưa có dữ liệu'}
            </span>
          </div>

          <div className="payment-item">
            <span className="payment-item-label">Tổng tiền</span>
            <span className="payment-item-value">{formatAmount(totalAmount) || 'Tính tại backend'}</span>
          </div>
        </div>

        <div className="payment-note">
          Backend xử lý thanh toán bất đồng bộ. Trang này sẽ tự cập nhật kết quả thành công hoặc thất bại theo log của payment-service.
        </div>

        <div className="payment-actions">
          {paymentUrl ? (
            <a className="payment-btn payment-btn-primary" href={paymentUrl} target="_blank" rel="noreferrer">
              Mở trang thanh toán
            </a>
          ) : (
            <button className="payment-btn payment-btn-disabled" type="button" disabled>
              Chưa có đường dẫn thanh toán từ backend
            </button>
          )}

          <button
            className="payment-btn payment-btn-info"
            type="button"
            onClick={refreshPaymentStatus}
            disabled={!bookingId || isRefreshing}
          >
            {isRefreshing ? 'Đang cập nhật...' : 'Cập nhật trạng thái'}
          </button>

          <button
            className="payment-btn payment-btn-outline"
            type="button"
            onClick={() => setIsAutoRefreshEnabled((prev) => !prev)}
          >
            {isAutoRefreshEnabled ? 'Dừng tự động cập nhật' : 'Bật tự động cập nhật'}
          </button>

          <button
            className="payment-btn payment-btn-secondary"
            type="button"
            onClick={() => navigate('/movies')}
          >
            Về danh sách phim
          </button>

          <Link className="payment-btn payment-btn-info" to="/booking-history">
            Lịch sử đặt vé
          </Link>

          <Link className="payment-btn payment-btn-ghost" to={movieId ? `/book/${movieId}` : '/movies'}>
            Đặt lại
          </Link>
        </div>
      </section>
    </main>
  );
}

export default Payment;
