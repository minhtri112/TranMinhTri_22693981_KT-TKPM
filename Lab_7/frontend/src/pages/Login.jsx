import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

const USER_SERVICE_URL = 'http://172.16.56.67:8081';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLogin, setIsLogin] = useState(true);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const endpoint = isLogin ? '/login' : '/register';
      const response = await api.post(`${USER_SERVICE_URL}${endpoint}`, { username, password });
      
      if (isLogin) {
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify(response.data.user));
        navigate('/movies');
      } else {
        alert('Đăng ký thành công! Vui lòng đăng nhập.');
        setIsLogin(true);
      }
    } catch (error) {
      alert('Lỗi: ' + (error.response?.data?.message || error.message));
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <h2>{isLogin ? 'Đăng nhập' : 'Đăng ký'}</h2>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <input 
          type="text" 
          placeholder="Tên đăng nhập" 
          value={username} 
          onChange={e => setUsername(e.target.value)} 
          required 
          style={{ padding: '8px' }}
        />
        <input 
          type="password" 
          placeholder="Mật khẩu" 
          value={password} 
          onChange={e => setPassword(e.target.value)} 
          required 
          style={{ padding: '8px' }}
        />
        <button type="submit" style={{ padding: '10px', background: '#007bff', color: 'white', border: 'none', cursor: 'pointer' }}>
          {isLogin ? 'Đăng nhập' : 'Đăng ký'}
        </button>
      </form>
      <p style={{ marginTop: '10px', cursor: 'pointer', color: 'blue' }} onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? 'Chưa có tài khoản? Đăng ký' : 'Đã có tài khoản? Đăng nhập'}
      </p>
    </div>
  );
}

export default Login;
