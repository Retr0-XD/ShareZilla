import React from "react";
import Link from "next/link";
import { useRouter } from "next/router";

const Navbar = () => {
  const router = useRouter();

  const handleLogout = () => {
    localStorage.removeItem("token");
    alert("Logged out successfully!");
    router.push("/login");
  };

  return (
    <nav style={{ padding: "10px", borderBottom: "1px solid #ccc" }}>
      <Link href="/files" style={{ marginRight: "10px" }}>
        Files
      </Link>
      <Link href="/public-data" style={{ marginRight: "10px" }}>
        Public Data
      </Link>
      <button onClick={handleLogout} style={{ marginLeft: "10px" }}>
        Logout
      </button>
    </nav>
  );
};

export default Navbar;