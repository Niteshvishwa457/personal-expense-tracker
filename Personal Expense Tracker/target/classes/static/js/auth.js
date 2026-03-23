const API_BASE_URL = 'http://localhost:8082/api';

const auth = {
    getToken: () => localStorage.getItem('token'),
    setToken: (token) => localStorage.setItem('token', token),
    setUser: (user) => localStorage.setItem('user', JSON.stringify(user)),
    getUser: () => JSON.parse(localStorage.getItem('user')),
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        window.location.href = '/pages/login.html';
    },
    isLoggedIn: () => !!localStorage.getItem('token'),
    getHeaders: () => ({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
    })
};

async function apiCall(endpoint, method = 'GET', body = null, isBlob = false) {
    const options = {
        method,
        headers: auth.getHeaders()
    };
    if (body) options.body = JSON.stringify(body);

    const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
    
    if (response.status === 401 || response.status === 403) {
        auth.logout();
        return;
    }

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'API request failed');
    }

    if (isBlob) return await response.blob();
    return await response.json();
}

// Redirect if not logged in
if (!window.location.pathname.includes('login.html') && 
    !window.location.pathname.includes('signup.html') && 
    !auth.isLoggedIn()) {
    window.location.href = '/pages/login.html';
}
