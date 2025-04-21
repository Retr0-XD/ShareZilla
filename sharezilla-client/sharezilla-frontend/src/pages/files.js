import React, { useState, useEffect } from "react";
import axios from "axios";
import withAuth from "../utils/withAuth";
import Navbar from "../components/Navbar";
import "../app/globals.css"; // Import global styles
import config from "../config"; // Import the backend base URL

const Files = () => {
  const [files, setFiles] = useState([]);
  const [file, setFile] = useState(null);

  useEffect(() => {
    axios
      .get(`${config.apiBaseUrl}/api/files/list`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then((response) => setFiles(response.data))
      .catch((error) => console.error("Error fetching files:", error));
  }, []);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const uploadFile = () => {
    const formData = new FormData();
    formData.append("file", file);

    axios
      .post(`${config.apiBaseUrl}/api/files/upload`, formData, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then((response) => {
        setFiles([...files, response.data]);
        alert("File uploaded successfully");
        setFile(null); // Clear the file input
      })
      .catch((error) => alert("Error uploading file"));
  };

  const downloadFile = (filename) => {
    axios
      .get(`${config.apiBaseUrl}/api/files/download/${filename}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
        responseType: "blob",
      })
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", filename);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link); // Clean up the DOM
      })
      .catch((error) => alert("Error downloading file"));
  };

  const deleteFile = (filename) => {
    axios
      .delete(`${config.apiBaseUrl}/api/files/delete/${filename}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then(() => {
        setFiles(files.filter((f) => f.filename !== filename));
        alert("File deleted successfully");
      })
      .catch((error) => alert("Error deleting file"));
  };

  return (
    <>
      <Navbar />
      <div className="container mx-auto mt-10 p-6 bg-white shadow-md rounded-md">
        <h2 className="text-2xl font-bold mb-6 text-center">File Management</h2>

        <form
          onSubmit={(e) => {
            e.preventDefault();
            uploadFile();
          }}
          className="mb-8 p-6 bg-blue-600 rounded-md shadow-md"
        >
          <h3 className="text-xl font-bold mb-4">Upload a File</h3>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">Select File</label>
            <input
              type="file"
              onChange={handleFileChange}
              required
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring focus:ring-blue-300"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-black text-white py-2 px-4 rounded-md hover:bg-black/80 transition"
          >
            Upload
          </button>
        </form>

        <h3 className="text-xl font-bold mb-4">Uploaded Files</h3>
        <ul className="space-y-4">
          {files.map((file) => (
            <li
              key={file.filename}
              className="p-4 border rounded-md flex justify-between items-center"
            >
              <span>{file.filename}</span>
              <div className="flex space-x-4">
                <button
                  onClick={() => downloadFile(file.filename)}
                  className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
                >
                  Download
                </button>
                <button
                  onClick={() => deleteFile(file.filename)}
                  className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600"
                >
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default withAuth(Files, ["UPLOADER", "ADMIN"]);