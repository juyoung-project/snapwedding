import type { NextConfig } from 'next';

const nextConfig: NextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: 'http',
        hostname: 'localhost',
        port: '8090',
        pathname: '/api/file/file-download/**',
      },
    ],
  },
};

export default nextConfig;
