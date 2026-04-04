const express = require("express");
const mysql = require("mysql2");

const app = express();
app.use(express.json());

const connection = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
});

// Kết nối DB
connection.connect((err) => {
  if (err) {
    console.error("❌ Lỗi kết nối MySQL:", err);
    return;
  }
  console.log("✅ Kết nối MySQL thành công!");
});


// ================== CREATE ==================
app.post("/users", (req, res) => {
  const { name, email } = req.body;

  const sql = "INSERT INTO users (name, email) VALUES (?, ?)";
  
  connection.query(sql, [name, email], (err, result) => {
    if (err) {
      console.error(err);
      return res.send("Lỗi khi thêm dữ liệu");
    }
    res.send("✅ Thêm user thành công!");
  });
});


// ================== READ ==================
app.get("/users", (req, res) => {
  const sql = "SELECT * FROM users";

  connection.query(sql, (err, results) => {
    if (err) {
      console.error(err);
      return res.send("Lỗi khi lấy dữ liệu");
    }
    res.json(results);
  });
});


// Test
app.get("/", (req, res) => {
  res.send("Server OK 🚀");
});

app.listen(3000, () => {
  console.log("Server chạy tại port 3000");
});