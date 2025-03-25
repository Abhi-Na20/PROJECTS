document.getElementById('contactBtn').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default anchor behavior
    alert('Contact us at: ulstermotors@gmail.com');
});


document.getElementById('learnMoreBtn').addEventListener('click', function() {
    alert('Thank you for your interest! More information will be available soon.');
});
