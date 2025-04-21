import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-50 to-slate-100 text-gray-800">
      {/* Navigation */}
      <nav className="bg-white shadow-sm py-4 px-6">
        <div className="container mx-auto flex justify-between items-center">
          <span className="text-2xl font-bold text-blue-600">ShareZilla</span>
          <div className="flex gap-4">
            <Link
              href="/login"
              className="text-blue-600 hover:text-blue-800 transition"
            >
              Log in
            </Link>
            <Link
              href="/register"
              className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
            >
              Sign up
            </Link>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <header className="relative bg-gradient-to-r from-blue-600 to-blue-500 text-white py-28">
        <div className="absolute inset-0 bg-pattern opacity-10"></div>
        <div className="container mx-auto px-6 text-center relative z-10">
          <h1 className="text-5xl md:text-6xl font-extrabold mb-6 leading-tight">
            Secure File Sharing <br className="hidden md:block" />
            <span className="text-blue-200">Made Simple</span>
          </h1>
          <p className="text-xl md:text-2xl mb-10 max-w-2xl mx-auto text-blue-100">
            The ultimate platform for secure file sharing and management for teams and individuals.
          </p>
          <div className="flex flex-col sm:flex-row justify-center gap-4">
            <Link
              href="/register"
              className="bg-white text-blue-600 px-8 py-4 rounded-lg font-semibold hover:bg-blue-50 transform hover:-translate-y-1 transition duration-300 shadow-lg"
            >
              Get Started Free
            </Link>
            <Link
              href="/login"
              className="bg-blue-700 px-8 py-4 rounded-lg font-semibold hover:bg-blue-800 transform hover:-translate-y-1 transition duration-300 text-white shadow-lg"
            >
              Sign In
            </Link>
          </div>
        </div>
      </header>

      {/* About Section */}
      <section className="py-24">
        <div className="container mx-auto px-6">
          <div className="max-w-3xl mx-auto text-center">
            <h2 className="text-4xl font-bold mb-8 text-blue-900">About ShareZilla</h2>
            <p className="text-lg text-gray-700 leading-relaxed">
              ShareZilla is a secure and user-friendly platform designed to make file
              sharing and management effortless. Whether you're an individual, a team,
              or an organization, ShareZilla provides the tools you need to upload,
              share, and manage files with ease and confidence.
            </p>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-24 bg-gradient-to-b from-gray-50 to-gray-100">
        <div className="container mx-auto px-6">
          <h2 className="text-4xl font-bold text-center mb-16 text-blue-900">What You Can Do</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {features.map((feature, index) => (
              <div 
                key={index}
                className="bg-white p-8 rounded-xl shadow-lg hover:shadow-xl transition duration-300 text-center group"
              >
                <div className="w-16 h-16 mx-auto mb-6 flex items-center justify-center bg-blue-100 text-blue-600 rounded-full group-hover:bg-blue-600 group-hover:text-white transition duration-300">
                  {/* Simple icon representation */}
                  <span className="text-2xl">{feature.icon}</span>
                </div>
                <h3 className="text-2xl font-bold mb-4 text-gray-800 group-hover:text-blue-600 transition duration-300">
                  {feature.title}
                </h3>
                <p className="text-gray-600">
                  {feature.description}
                </p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Call to Action Section */}
      <section className="bg-gradient-to-r from-blue-600 to-blue-500 text-white py-24">
        <div className="container mx-auto px-6 text-center">
          <h2 className="text-4xl font-bold mb-6">Ready to Get Started?</h2>
          <p className="text-xl mb-10 max-w-2xl mx-auto">
            Join ShareZilla today and experience seamless file sharing with enhanced security and control.
          </p>
          <Link
            href="/register"
            className="bg-white text-blue-600 px-10 py-4 rounded-lg font-semibold hover:bg-blue-50 transform hover:-translate-y-1 transition duration-300 shadow-lg inline-block"
          >
            Sign Up Now — It's Free
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-16">
        <div className="container mx-auto px-6">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
            <div>
              <h3 className="text-xl font-bold text-white mb-4">ShareZilla</h3>
              <p className="mb-4">Secure file sharing for everyone.</p>
            </div>
            <div>
              <h4 className="text-lg font-semibold text-white mb-4">Product</h4>
              <ul className="space-y-2">
                <li><a href="#" className="hover:text-white transition">Features</a></li>
                <li><a href="#" className="hover:text-white transition">Pricing</a></li>
                <li><a href="#" className="hover:text-white transition">Security</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-lg font-semibold text-white mb-4">Resources</h4>
              <ul className="space-y-2">
                <li><a href="#" className="hover:text-white transition">Documentation</a></li>
                <li><a href="#" className="hover:text-white transition">Support</a></li>
                <li><a href="#" className="hover:text-white transition">FAQ</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-lg font-semibold text-white mb-4">Company</h4>
              <ul className="space-y-2">
                <li><a href="#" className="hover:text-white transition">About</a></li>
                <li><a href="#" className="hover:text-white transition">Careers</a></li>
                <li><a href="#" className="hover:text-white transition">Contact</a></li>
              </ul>
            </div>
          </div>
          <div className="pt-8 border-t border-gray-800 text-center">
            <p>&copy; {new Date().getFullYear()} ShareZilla. All rights reserved.</p>
            <div className="flex justify-center gap-6 mt-4">
              <a
                href="https://github.com"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:text-white transition"
              >
                GitHub
              </a>
              <a
                href="https://nextjs.org"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:text-white transition"
              >
                Next.js
              </a>
              <a
                href="https://vercel.com"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:text-white transition"
              >
                Vercel
              </a>
            </div>
          </div>
        </div>
      </footer>
    </div>
  );
}

// Feature data
const features = [
  {
    icon: "🔒",
    title: "Secure File Sharing",
    description: "Share files with end-to-end encryption to ensure your data stays safe and private."
  },
  {
    icon: "⚡",
    title: "Effortless Uploads",
    description: "Upload files of any size with our intuitive and lightning-fast upload system."
  },
  {
    icon: "👥",
    title: "Role-Based Access",
    description: "Assign roles like Admin, Uploader, or User to control file access precisely."
  },
  {
    icon: "🌐",
    title: "Public File Sharing",
    description: "Share files publicly with a simple link for easy access when you need to."
  },
  {
    icon: "⚙️",
    title: "Admin Tools",
    description: "Manage users and files with powerful admin tools for better control and oversight."
  },
  {
    icon: "📱",
    title: "Cross-Platform Access",
    description: "Access your files from any device, anywhere, at any time with responsive design."
  }
];
