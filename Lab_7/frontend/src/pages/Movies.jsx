import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

const MOVIE_SERVICE_URL = 'http://172.16.56.112:8082';

function Movies() {
  const [movies, setMovies] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const response = await api.get(`${MOVIE_SERVICE_URL}/movies`);
        setMovies(response.data);
      } catch (error) {
        console.error('Không thể tải danh sách phim', error);
      }
    };
    fetchMovies();
  }, []);

  const handleBook = (movie) => {
    const id = movie?._id || movie?.id;
    if (!id) return;

    navigate(`/book/${id}`, {
      state: {
        movie
      }
    });
  };

  return (
    <div style={{ padding: '20px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2>Danh sách phim</h2>
        <div style={{ display: 'flex', gap: '8px' }}>
          <button
            onClick={() => navigate('/booking-history')}
            style={{ padding: '5px 10px', background: '#2563eb', color: 'white', border: 'none', cursor: 'pointer' }}
          >
            Lịch sử đặt vé
          </button>
          <button onClick={() => { localStorage.clear(); navigate('/'); }} style={{ padding: '5px 10px', background: '#dc3545', color: 'white', border: 'none', cursor: 'pointer' }}>
            Đăng xuất
          </button>
        </div>
      </div>
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px', marginTop: '20px' }}>
        {movies.map(movie => (
          <div key={movie._id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px', width: '250px' }}>
            <h3>{movie.title}</h3>
            <p>{movie.description}</p>
            <p><strong>Giá:</strong> {movie.price} VND</p>
            <p><strong>Suất chiếu:</strong> {movie.showtimes.join(', ')}</p>
            <button onClick={() => handleBook(movie)} style={{ padding: '8px 15px', background: '#28a745', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', marginTop: '10px' }}>
              Đặt vé
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Movies;
