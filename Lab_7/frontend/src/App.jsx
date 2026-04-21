import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Movies from './pages/Movies';
import Booking from './pages/Booking';
import Payment from './pages/Payment';
import BookingHistory from './pages/BookingHistory';

const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem('token');
  if (!token) return <Navigate to="/" replace />;
  return children;
};

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/movies" element={
          <ProtectedRoute>
            <Movies />
          </ProtectedRoute>
        } />
        <Route path="/book/:movieId" element={
          <ProtectedRoute>
            <Booking />
          </ProtectedRoute>
        } />
        <Route path="/payment" element={
          <ProtectedRoute>
            <Payment />
          </ProtectedRoute>
        } />
        <Route path="/booking-history" element={
          <ProtectedRoute>
            <BookingHistory />
          </ProtectedRoute>
        } />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
