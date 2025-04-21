import { useEffect } from "react";
import { useRouter } from "next/router";

const withAuth = (WrappedComponent, allowedRoles) => {
  const WithAuthComponent = (props) => {
    const router = useRouter();

    useEffect(() => {
      // Ensure this code runs only on the client side
      if (typeof window !== "undefined") {
        const token = localStorage.getItem("token");

        if (!token) {
          alert("You must be logged in to access this page.");
          router.push("/login");
          return;
        }

        try {
          const payload = JSON.parse(atob(token.split(".")[1])); // Decode JWT payload
          const userRole = payload.role;

          if (!allowedRoles.includes(userRole)) {
            alert("You do not have permission to access this page.");
            router.push("/");
          }
        } catch (error) {
          console.error("Invalid token:", error);
          alert("Invalid token. Please log in again.");
          router.push("/login");
        }
      }
    }, [router]);

    return <WrappedComponent {...props} />;
  };

  // Set the display name for debugging
  WithAuthComponent.displayName = `WithAuth(${WrappedComponent.displayName || WrappedComponent.name || "Component"})`;

  return WithAuthComponent;
};

export default withAuth;