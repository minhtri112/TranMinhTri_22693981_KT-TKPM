import React, { useEffect, useMemo, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import api from '../api';
import './Booking.css';

const BOOKING_SERVICE_URL = 'http://172.16.56.102:8083';
const MOVIE_SERVICE_URL = 'http://172.16.56.112:8082';
const SEAT_ROWS = ['A', 'B', 'C', 'D', 'E', 'F'];
const SEATS_PER_ROW = 8;
const ALL_SEATS = SEAT_ROWS.flatMap((row) =>
  Array.from({ length: SEATS_PER_ROW }, (_, index) => `${row}${index + 1}`)
);

const sortSeatCodes = (seatCodes) => {
  return [...seatCodes].sort((a, b) => {
    const rowA = a.charCodeAt(0);
    const rowB = b.charCodeAt(0);
    const numA = Number(a.slice(1));
    const numB = Number(b.slice(1));

    if (rowA !== rowB) return rowA - rowB;
    return numA - numB;
  });
};

const normalizeMoviePayload = (payload) => {
  if (!payload || typeof payload !== 'object' || Array.isArray(payload)) return null;
  if (payload.data && typeof payload.data === 'object' && !Array.isArray(payload.data)) {
    return payload.data;
  }
  return payload;
};

const normalizeMovieListPayload = (payload) => {
  if (Array.isArray(payload)) return payload;
  if (Array.isArray(payload?.data)) return payload.data;
  if (Array.isArray(payload?.movies)) return payload.movies;
  return [];
};

const getMoviePrice = (movie) => {
  const parsed = Number(movie?.price ?? movie?.ticketPrice ?? movie?.ticket_price ?? 0);
  return Number.isFinite(parsed) ? parsed : 0;
};

const formatCurrency = (amount) => `${new Intl.NumberFormat('vi-VN').format(amount)} VND`;

function Booking() {
  const { movieId } = useParams();
  const location = useLocation();
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [fetchedMovie, setFetchedMovie] = useState(null);
  const [isMovieLoading, setIsMovieLoading] = useState(false);
  const navigate = useNavigate();

  const movie = location.state?.movie || fetchedMovie;

  const seatPrice = useMemo(() => getMoviePrice(movie), [movie]);
  const totalAmount = seatPrice * selectedSeats.length;

  useEffect(() => {
    if (location.state?.movie) return;

    if (!movieId) return;

    let isCancelled = false;

    const fetchMovieInfo = async () => {
      setIsMovieLoading(true);
      try {
        const detailResponse = await api.get(`${MOVIE_SERVICE_URL}/movies/${movieId}`);
        const normalizedMovie = normalizeMoviePayload(detailResponse.data);
        if (!isCancelled && normalizedMovie) {
          setFetchedMovie(normalizedMovie);
          return;
        }
      } catch {
        // Ignore detail endpoint failures and try the list endpoint.
      }

      try {
        const listResponse = await api.get(`${MOVIE_SERVICE_URL}/movies`);
        const movies = normalizeMovieListPayload(listResponse.data);
        const matchedMovie = movies.find(
          (item) => String(item?._id || item?.id) === String(movieId)
        );
        if (!isCancelled && matchedMovie) {
          setFetchedMovie(matchedMovie);
        }
      } catch (error) {
        console.error('Failed to fetch movie data for booking', error);
      } finally {
        if (!isCancelled) {
          setIsMovieLoading(false);
        }
      }
    };

    fetchMovieInfo();

    return () => {
      isCancelled = true;
    };
  }, [movieId, location.state?.movie]);

  const toggleSeat = (seatCode) => {
    setSelectedSeats((prevSeats) => {
      if (prevSeats.includes(seatCode)) {
        return prevSeats.filter((seat) => seat !== seatCode);
      }

      return sortSeatCodes([...prevSeats, seatCode]);
    });
  };

  const handleBook = async (e) => {
    e.preventDefault();
    let user = null;
    try {
      user = JSON.parse(localStorage.getItem('user') || 'null');
    } catch {
      user = null;
    }

    const userId = user?.id || user?._id || user?.userId;

    if (!user || !userId) {
      alert('Vui lòng đăng nhập trước');
      navigate('/');
      return;
    }

    try {
      if (selectedSeats.length === 0) {
        return alert('Vui lòng chọn ít nhất một ghế');
      }

      const payload = {
        userId,
        movieId,
        seats: selectedSeats
      };

      const response = await api.post(`${BOOKING_SERVICE_URL}/bookings`, payload);
      navigate('/payment', {
        state: {
          booking: response.data,
          movieId,
          selectedSeats,
          totalAmount,
          movie
        }
      });
    } catch (error) {
      const status = error.response?.status;
      const backendMessage =
        error.response?.data?.message ||
        (typeof error.response?.data === 'string'
          ? error.response.data
          : error.message);

      console.error('Booking creation failed', {
        status,
        responseData: error.response?.data,
        requestPayload: {
          userId,
          movieId,
          seats: selectedSeats,
          totalAmount
        }
      });

      alert(
        `Tạo đặt vé thất bại${status ? ` (HTTP ${status})` : ''}: ${backendMessage}`
      );
    }
  };

  return (
    <main className="booking-page">
      <div className="booking-glow booking-glow-left" />
      <div className="booking-glow booking-glow-right" />

      <section className="booking-card">
        <p className="booking-overline">Đặt vé nhanh</p>
        <h2>Đặt vé xem phim</h2>
        <p className="booking-movie-id">
          Phim: {movie?.title || movieId}
          {isMovieLoading ? ' (đang tải giá...)' : ''}
        </p>

        <form onSubmit={handleBook} className="booking-form">
          <div className="booking-label-row">
            <p className="booking-label">Chọn ghế của bạn</p>
            <p className="booking-seat-count">Đã chọn {selectedSeats.length}</p>
          </div>

          <div className="booking-screen" aria-hidden="true">
            <span>Màn hình</span>
          </div>

          <div className="booking-seat-grid" role="group" aria-label="Cinema seats">
            {ALL_SEATS.map((seatCode) => {
              const isSelected = selectedSeats.includes(seatCode);

              return (
                <button
                  key={seatCode}
                  type="button"
                  className={`booking-seat-button ${isSelected ? 'is-selected' : ''}`}
                  onClick={() => toggleSeat(seatCode)}
                  aria-pressed={isSelected}
                >
                  {seatCode}
                </button>
              );
            })}
          </div>

          <div className="booking-legend" aria-hidden="true">
            <div className="booking-legend-item">
              <span className="booking-legend-dot" />
              <span>Ghế trống</span>
            </div>
            <div className="booking-legend-item">
              <span className="booking-legend-dot is-selected" />
              <span>Đã chọn</span>
            </div>
          </div>

          <div className="booking-seat-preview" aria-live="polite">
            {selectedSeats.length > 0 ? (
              selectedSeats.map((seat) => (
                <span key={seat} className="booking-seat-chip">
                  {seat}
                </span>
              ))
            ) : (
              <span className="booking-seat-hint">Nhấn vào ghế trên sơ đồ để chọn</span>
            )}
          </div>

          <div className="booking-summary" aria-live="polite">
            <div className="booking-summary-row">
              <span>Giá mỗi ghế</span>
              <strong>{seatPrice > 0 ? formatCurrency(seatPrice) : 'Chưa có dữ liệu giá'}</strong>
            </div>
            <div className="booking-summary-row">
              <span>Số ghế đã chọn</span>
              <strong>{selectedSeats.length}</strong>
            </div>
            <div className="booking-summary-row booking-summary-total">
              <span>Tổng tiền</span>
              <strong>{selectedSeats.length > 0 ? formatCurrency(totalAmount) : '0 VND'}</strong>
            </div>
          </div>

          <div className="booking-actions booking-actions-three">
            <button type="submit" className="btn btn-primary">
              Thanh toán ngay
            </button>
            <button
              type="button"
              onClick={() => navigate('/booking-history')}
              className="btn btn-info"
            >
              Xem lịch sử đặt vé
            </button>
            <button
              type="button"
              onClick={() => navigate('/movies')}
              className="btn btn-secondary"
            >
              Quay lại
            </button>
          </div>
        </form>
      </section>
    </main>
  );
}

export default Booking;
