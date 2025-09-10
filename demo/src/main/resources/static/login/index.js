document.addEventListener("DOMContentLoaded", () => {
    const errorMessage = document.getElementById("error-message");

    if (window.location.search.includes("error")) {
        errorMessage.textContent = "Invalid username or password.";
    }

    if (window.location.search.includes("logout")) {
        errorMessage.textContent = "You have been logged out.";
    }
});
