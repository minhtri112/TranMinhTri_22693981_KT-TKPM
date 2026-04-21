import React, { useEffect, useMemo, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';
import './BookingHistory.css';

const BOOKING_SERVICE_URL = 'http://172.16.56.102:8083';

const readFirst = (...values) => values.find((value) => value !== undefined && value !== null && value !== '');

const normalizeBookingListPayload = (payload) => {
  if (Array.isArray(payload)) return payload;
  if (Array.isArray(payload?.data)) return payload.data;
  if (Array.isArray(payload?.bookings)) return payload.bookings;
  if (Array.isArray(payload?.data?.bookings)) return payload.data.bookings;
  return [];
};

const getUserIdFromStorage = () => {
  try {
    const user = JSON.parse(localStorage.getItem('user') || 'null');
    return user?.id || user?._id || user?.userId || null;
  } catch {
    return null;
  }
};

const formatMoney = (value) => {
  const amount = Number(value);
  if (!Number.isFinite(amount)) return 'Chưa có giá';
  return `${new Intl.NumberFormat('vi-VN').format(amount)} VND`;
};

const formatDateTime = (value) => {
  if (!value) return 'Chưa có thời gian';
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return String(value);
  return date.toLocaleString('vi-VN');
};

const getBookingId = (booking) =>
  readFirst(booking?.bookingId, booking?._id, booking?.id, booking?.data?.bookingId);

const getMovieId = (booking) => {
  const rawMovie = readFirst(booking?.movieId, booking?.movie, booking?.data?.movieId);
  if (rawMovie && typeof rawMovie === 'object') {
    return readFirst(rawMovie._id, rawMovie.id, rawMovie.movieId);
  }
  return rawMovie;
};

const getMovieLabel = (booking) => {
  const rawMovie = readFirst(booking?.movie, booking?.movieId, booking?.data?.movie);
  if (rawMovie && typeof rawMovie === 'object') {
    return readFirst(rawMovie.title, rawMovie._id, rawMovie.id, 'Phim không xác định');
  }
  return rawMovie || 'Phim không xác định';
};

const getSeats = (booking) => {
  const seats = readFirst(booking?.seats, booking?.selectedSeats, booking?.data?.seats);
  return Array.isArray(seats) ? seats : [];
};

const getAmount = (booking) =>
  readFirst(booking?.totalAmount, booking?.amount, booking?.total, booking?.price, booking?.data?.totalAmount);

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

const getStatusMeta = (booking) => {
  const rawStatus = String(readFirst(booking?.paymentStatus, booking?.status, 'processing')).toLowerCase();

  if (['paid', 'success', 'completed', 'done', 'confirmed'].includes(rawStatus)) {
    return {
      className: 'is-success',
      text: 'Thành công'
    };
  }

  if (['failed', 'cancelled', 'canceled', 'error'].includes(rawStatus)) {
    return {
      className: 'is-failed',
      text: 'Thất bại'
    };
  }

  return {
    className: 'is-processing',
    text: 'Đang xử lý'
  };
};

const fetchBookingHistoryByUser = async (userId) => {
  const endpointCandidates = [
    `${BOOKING_SERVICE_URL}/bookings/user/${userId}`,
    `${BOOKING_SERVICE_URL}/bookings/history/${userId}`,
    `${BOOKING_SERVICE_URL}/bookings?userId=${encodeURIComponent(userId)}`
  ];

  let lastError = null;

  for (const endpoint of endpointCandidates) {
    try {
      const response = await api.get(endpoint);
      return normalizeBookingListPayload(response.data);
    } catch (error) {
      lastError = error;
      if (error.response?.status === 404) {
        continue;
      }
      throw error;
    }
  }

  if (lastError?.response?.status === 404) {
    return [];
  }

  throw lastError || new Error('Failed to fetch booking history');
};

function BookingHistory() {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    let isCancelled = false;

    const fetchHistory = async () => {
      const userId = getUserIdFromStorage();
      if (!userId) {
        setErrorMessage('Vui lòng đăng nhập lại để xem lịch sử đặt vé.');
        setIsLoading(false);
        return;
      }

      try {
        setIsLoading(true);
        const history = await fetchBookingHistoryByUser(userId);
        if (!isCancelled) {
          setBookings(history);
          setErrorMessage('');
        }
      } catch (error) {
        console.error('Failed to fetch booking history', error);
        if (!isCancelled) {
          const status = error.response?.status;
          const backendMessage =
            error.response?.data?.message ||
            (typeof error.response?.data === 'string' ? error.response.data : error.message);

          setErrorMessage(
            `Không thể tải lịch sử đặt vé${status ? ` (HTTP ${status})` : ''}: ${backendMessage}`
          );
        }
      } finally {
        if (!isCancelled) {
          setIsLoading(false);
        }
      }
    };

    fetchHistory();

    return () => {
      isCancelled = true;
    };
  }, []);

  const sortedBookings = useMemo(() => {
    return [...bookings].sort((a, b) => {
      const timeA = new Date(readFirst(a?.createdAt, a?.created_at, a?.bookingDate, 0)).getTime() || 0;
      const timeB = new Date(readFirst(b?.createdAt, b?.created_at, b?.bookingDate, 0)).getTime() || 0;
      return timeB - timeA;
    });
  }, [bookings]);

  return (
    <main className="history-page">
      <div className="history-glow history-glow-left" />
      <div className="history-glow history-glow-right" />

      <section className="history-card">
        <div className="history-header">
          <div>
            <p className="history-overline">Lịch sử</p>
            <h2>Lịch sử đặt vé</h2>
          </div>
          <div className="history-header-actions">
            <button type="button" className="history-btn history-btn-secondary" onClick={() => navigate('/movies')}>
              Về danh sách phim
            </button>
            <button type="button" className="history-btn history-btn-primary" onClick={() => navigate(-1)}>
              Quay lại
            </button>
          </div>
        </div>

        {isLoading ? <p className="history-state">Đang tải lịch sử đặt vé...</p> : null}

        {!isLoading && errorMessage ? <p className="history-state history-state-error">{errorMessage}</p> : null}

        {!isLoading && !errorMessage && sortedBookings.length === 0 ? (
          <p className="history-state">Chưa có lịch sử đặt vé.</p>
        ) : null}

        {!isLoading && !errorMessage && sortedBookings.length > 0 ? (
          <div className="history-list">
            {sortedBookings.map((booking, index) => {
              const bookingId = getBookingId(booking) || `booking-${index + 1}`;
              const movieId = getMovieId(booking);
              const movieLabel = getMovieLabel(booking);
              const seats = getSeats(booking);
              const statusMeta = getStatusMeta(booking);
              const paymentUrl = getPaymentUrl(booking);
              const createdAt = readFirst(booking?.createdAt, booking?.created_at, booking?.bookingDate);

              return (
                <article key={`${bookingId}-${index}`} className="history-item">
                  <div className="history-item-top">
                    <span className="history-item-id">{bookingId}</span>
                    <span className={`history-status ${statusMeta.className}`}>{statusMeta.text}</span>
                  </div>

                  <div className="history-item-grid">
                    <p>
                      <strong>Phim:</strong> {movieLabel}
                    </p>
                    <p>
                      <strong>Ghế:</strong> {seats.length > 0 ? seats.join(', ') : 'Chưa có ghế'}
                    </p>
                    <p>
                      <strong>Tổng tiền:</strong> {formatMoney(getAmount(booking))}
                    </p>
                    <p>
                      <strong>Thời gian tạo:</strong> {formatDateTime(createdAt)}
                    </p>
                  </div>

                  <div className="history-item-actions">
                    {paymentUrl ? (
                      <a
                        href={paymentUrl}
                        target="_blank"
                        rel="noreferrer"
                        className="history-btn history-btn-primary"
                      >
                        Thanh toán
                      </a>
                    ) : (
                      <Link to="/payment" state={{ booking }} className="history-btn history-btn-ghost">
                        Xem thanh toán
                      </Link>
                    )}

                    <Link
                      to={movieId ? `/book/${movieId}` : '/movies'}
                      className="history-btn history-btn-secondary"
                    >
                      Đặt lại
                    </Link>
                  </div>
                </article>
              );
            })}
          </div>
        ) : null}
      </section>
    </main>
  );
}

export default BookingHistory;
