document.getElementById('shortenForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const url = document.getElementById('originalUrl').value;
    const username = document.getElementById('username').value || 'anonymous';

    fetch('/shorten', {
        method: 'POST',
        body: `url=${encodeURIComponent(url)}&username=${encodeURIComponent(username)}`
    })
    .then(res => res.text())
    .then(shortUrl => {
        document.getElementById('shortenedResult').innerHTML = `Shortened URL: <a href="${shortUrl}" target="_blank">${shortUrl}</a>`;
    })
    .catch(err => alert('Error: ' + err));
});

function register() {
    auth('/register');
}

function login() {
    auth('/login');
}

function auth(endpoint) {
    const username = document.getElementById('authUsername').value;
    const password = document.getElementById('authPassword').value;

    fetch(endpoint, {
        method: 'POST',
        body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
    })
    .then(res => res.text())
    .then(result => alert(result))
    .catch(err => alert('Error: ' + err));
}
