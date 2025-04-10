function validateForm() {
    // Get values from the form fields
    const title = document.getElementById('Title').value;
    const fname = document.getElementById('fname').value.trim();
    const lname = document.getElementById('lname').value.trim();
    const age = document.getElementById('age').value;
    const email = document.getElementById('mail').value.trim();
    const pickupDate = document.getElementById('pickup').value;
    const returnDate = document.getElementById('return').value;
    const address1 = document.getElementById('Address').value.trim();
    const postcode = document.getElementById('Postcode').value.trim();
    const country = document.getElementById('Country').value.trim();
    const cardNumber = document.getElementById('CardNumber').value.trim();
    const expiryMonth = document.getElementById('DateMonth').value;
    const expiryYear = document.getElementById('DateYear').value;
    const cvv = document.getElementById('CVV').value.trim();

    // Check if required fields are empty
    if (!title || !fname || !lname || !age || !email || !pickupDate || !returnDate || !address1 || !postcode || !country || !cardNumber || !expiryMonth || !expiryYear || !cvv) {
        alert("Please fill out all required fields.");
        return false;
    }

    // Validate email format
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        alert("Please enter a valid email address.");
        return false;
    }

    // Validate card number
    const cardNumberPattern = /^[0-9\s]{13,19}$/;
    if (!cardNumberPattern.test(cardNumber.replace(/\s/g, ''))) {
        alert("Please enter a valid card number.");
        return false;
    }

    // Validate CVV
    const cvvPattern = /^[0-9]{3,4}$/;
    if (!cvvPattern.test(cvv)) {
        alert("Please enter a valid CVV (3 or 4 digits).");
        return false;
    }

    // Validate expiry date
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth() + 1;
    if (parseInt(expiryYear) < currentYear || (parseInt(expiryYear) === currentYear && parseInt(expiryMonth) < currentMonth)) {
        alert("Card expiry date cannot be in the past.");
        return false;
    }

    // Validate pickup and return date logic
    if (new Date(pickupDate) >= new Date(returnDate)) {
        alert("Return date must be after pickup date.");
        return false;
    }

    // All validations passed
    console.log("Validation passed. Redirecting...");

    // Redirect to confirmation
    window.location.href = 'confirmation.html';
    return true;
}

// Visual feedback for card selection
document.addEventListener('DOMContentLoaded', function () {
    const mastercardButton = document.getElementById('mastercard');
    const visaButton = document.getElementById('visa');
    const cardTypeInput = document.getElementById('cardType');

    if (mastercardButton && visaButton && cardTypeInput) {
        mastercardButton.addEventListener('click', function () {
            mastercardButton.style.border = '2px solid blue';
            visaButton.style.border = 'none';
            cardTypeInput.value = 'Mastercard';
        });

        visaButton.addEventListener('click', function () {
            visaButton.style.border = '2px solid blue';
            mastercardButton.style.border = 'none';
            cardTypeInput.value = 'Visa';
        });
    }
});
