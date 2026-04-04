from flask import Flask, request, jsonify

app = Flask(__name__)

# API test
@app.route("/")
def home():
    return "Flask is running 🚀"

# CREATE
@app.route("/users", methods=["POST"])
def create_user():
    data = request.get_json()
    return jsonify({
        "message": "User created",
        "data": data
    })

# READ
@app.route("/users", methods=["GET"])
def get_users():
    return jsonify([
        {"id": 1, "name": "Tri"},
        {"id": 2, "name": "An"}
    ])

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)