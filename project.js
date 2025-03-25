function toggleList() {
    // Access the List
  const courses = document.getElementById("courses_container");
  
    // Check the visibility
  if (courses.style.display == "block") {
      courses.style.display = "none"; // Hide the list
  } else {
      courses.style.display = "block"; // Display the list
  }
}

// Hide the list on click outside the dropdown list
window.onclick = function (event) {
  if (!event.target.matches('.dropdown_button')) {
      document.getElementById('courses_container')
          .style.display = "none";
  }
} 