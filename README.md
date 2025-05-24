
# 🚀 ShareZilla

**ShareZilla** is a lightweight, secure, and easy-to-use **local file sharing system** that allows users to share files within a local network. Built on **Spring Boot**, it offers fast file transfers without the need for cloud storage or internet access.

---

## 🧩 Features

- 📁 Upload and download files over the local network
- 🧑‍🤝‍🧑 Access shared files from any device in the same LAN
- 🚀 Built on Spring Boot for high performance and scalability
- 🔒 Secure file access with optional authentication
- 🌐 Simple and clean web interface

---

## ⚙️ Technologies Used

- **Backend**: Spring Boot (Java)
- **Frontend**: HTML, CSS, JavaScript (optional UI layer)
- **Database**: File-based or optional MySQL (configurable)
- **Network**: Operates over LAN/IP

---

## 📂 Project Structure

```

ShareZilla/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.sharezilla/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       └── ShareZillaApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
├── uploads/
├── README.md
└── pom.xml



---

## 🛠️ Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven 3.x
- LAN connection

### 🚀 Running the Application

```bash
# Clone the repository
git clone https://github.com/your-username/sharezilla.git
cd sharezilla

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run
````

Open your browser and go to:
**[http://localhost:8080](http://localhost:8080)**

To access from another device in the same network, use the host IP:
**http\://<your-local-ip>:8080**



## 🔐 Security (Optional)

Enable basic authentication or token-based access by configuring `application.properties`

---

## 🧪 Future Enhancements

* 📦 Folder sharing
* 🔐 Role-based access control
* 🌍 Multilingual interface
* 📲 Mobile-friendly UI

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 🧑‍💻 Author

Developed with 💻 by **Sakthi Harish S**

> Local file sharing, simplified — with ShareZilla.

```

---

Would you like me to include usage screenshots, Docker setup, or an OpenAPI (Swagger) section too?
```
