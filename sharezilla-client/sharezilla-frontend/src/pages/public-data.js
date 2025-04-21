import React, { useState, useEffect } from "react";
import axios from "axios";
import config from "../config";
import "../app/globals.css"; // Import global styles
import Navbar from "../components/Navbar";

const PublicData = () => {
  const [publicData, setPublicData] = useState([]);
  const [file, setFile] = useState(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    // Fetch public data
    axios
      .get(`${config.apiBaseUrl}/public-data`)
      .then((response) => setPublicData(response.data))
      .catch((error) => console.error("Error fetching public data:", error));

    // Check if the user is an admin
    const token = localStorage.getItem("token");
    if (token) {
      const userRole = JSON.parse(atob(token.split(".")[1])).role;
      setIsAdmin(userRole === "ADMIN");
    }
  }, []);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = (e) => {
    e.preventDefault();
    if (!file || !title || !content) {
      alert("Please provide a title, content, and select a file to upload.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("title", title);
    formData.append("content", content);

    axios
      .post(`${config.apiBaseUrl}/public-data`, formData, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then((response) => {
        setPublicData([...publicData, response.data]);
        alert("File uploaded successfully");
        setFile(null); // Clear the file input
        setTitle(""); // Clear the title input
        setContent(""); // Clear the content input
      })
      .catch((error) => alert("Error uploading file"));
  };

  const handleDownload = (id) => {
    axios
      .get(`${config.apiBaseUrl}/public-data/${id}/download`, {
        responseType: "blob",
      })
      .then((response) => {
        // Extract the file name from the Content-Disposition header
        const contentDisposition = response.headers["content-disposition"];
        let fileName = "downloaded-file";
        if (contentDisposition) {
          const fileNameMatch = contentDisposition.match(/filename="(.+)"/);
          if (fileNameMatch && fileNameMatch[1]) {
            fileName = fileNameMatch[1];
          }
        }

        // Create a URL for the file blob
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", fileName); // Use the extracted file name
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link); // Clean up the DOM
      })
      .catch((error) => alert("Error downloading file"));
  };

  const handleDelete = (id) => {
    if (!window.confirm("Are you sure you want to delete this public data?")) {
      return;
    }

    axios
      .delete(`${config.apiBaseUrl}/public-data/${id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then(() => {
        setPublicData(publicData.filter((data) => data.id !== id));
        alert("Public data deleted successfully");
      })
      .catch((error) => alert("Error deleting public data"));
  };

  return (
    <>
      <Navbar />
      <div className="container mx-auto mt-10 p-6 bg-white shadow-md rounded-md">
        <h2 className="text-2xl font-bold mb-6 text-center">Public Data</h2>
        <ul className="space-y-4">
          {publicData.map((data) => (
            <li
              key={data.id}
              className="p-4 border rounded-md flex justify-between items-center"
            >
              <div>
                <strong className="block text-lg">{data.title}</strong>
                <p className="text-sm text-gray-600">{data.content}</p>
              </div>
              <div className="flex space-x-4">
                <button
                  onClick={() => handleDownload(data.id)}
                  className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
                >
                  Download
                </button>
                {isAdmin && (
                  <button
                    onClick={() => handleDelete(data.id)}
                    className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600"
                  >
                    Delete
                  </button>
                )}
              </div>
            </li>
          ))}
        </ul>

        {isAdmin && (
          <form
            onSubmit={handleUpload}
            className="mt-8 p-6 bg-black-100 rounded-md shadow-md"
          >
            <h3 className="text-xl font-bold mb-4">Upload New Public Data</h3>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Title</label>
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:ring-blue-300"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Content</label>
              <textarea
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:ring-blue-300"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">File</label>
              <input
                type="file"
                onChange={handleFileChange}
                required
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:ring-blue-300"
              />
            </div>
            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
            >
              Upload
            </button>
          </form>
        )}
      </div>
    </>
  );
};

export default PublicData;