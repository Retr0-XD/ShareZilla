import os from "os";

const isProduction = process.env.NODE_ENV === "production";

// Function to get the correct local IPv4 address
const getLocalIp = () => {
  const networkInterfaces = os.networkInterfaces();
  let ipv4Address = "localhost"; // Default fallback

  for (const interfaceName in networkInterfaces) {
    const interfaces = networkInterfaces[interfaceName];
    for (const iface of interfaces) {
      // Check for IPv4 and non-internal addresses
      if (iface.family === "IPv4" && !iface.internal) {
        ipv4Address = iface.address; // Use the first valid IPv4 address
        break;
      }
    }
    if (ipv4Address !== "localhost") break; // Stop if a valid IPv4 address is found
  }

  return ipv4Address;
};

const localIp = getLocalIp();

const config = {
  apiBaseUrl: isProduction
    ? "http://<your-production-ip>:<port>" // Replace with your production IP and port
    : `http://${localIp}:8080`, // Use the dynamically detected local IP
};

console.log("API Base URL:", config.apiBaseUrl);

export default config;