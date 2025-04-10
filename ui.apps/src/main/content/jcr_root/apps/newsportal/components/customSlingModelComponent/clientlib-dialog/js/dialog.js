$(document).on("dialog-ready", function() {
    alert("Practice Component dialog opened..!!");
});
const person = {
  name: "John",
  age: 30,
  city: "New York"
};

// Display Properties
document.getElementById("demo").innerHTML =
person.name + "," + person.age + "," + person.city;