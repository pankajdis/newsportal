(function ($, window, document) {
  "use strict";

  $(document).on("dialog-ready", function () {

    // Select all multifields having a data-validation attribute like "multifield-max-5"
    $("[data-validation^='multifield-max']").each(function () {
      var $multifield = $(this);
      var validationName = $multifield.data("validation");

      // Extract max value (e.g., "multifield-max-5" → 5)
      var max = parseInt(validationName.replace("multifield-max-", ""), 10);
      if (isNaN(max)) {
        return; // invalid config
      }

      // Function to validate current multifield item count
      function validateCount() {
        var itemCount = $multifield.find(".coral-Multifield-item").length;

        // Remove any existing error messages
        $multifield.nextAll(".multifield-error").remove();

        if (itemCount > max) {
          // Show custom error message
          var errorMsg = $("<div>")
            .addClass("multifield-error")
            .css({
              color: "red",
              "margin-top": "5px",
              "font-size": "12px"
            })
            .text("Maximum allowed items is " + max + ". You have " + itemCount + ".");
          $multifield.after(errorMsg);
        }
      }

      // Initial validation when dialog opens
      validateCount();

      // Validate on add or remove of items
      $multifield.on("change", ".coral-Multifield", validateCount);
      $multifield.on("click", ".js-coral-Multifield-add", validateCount);
      $multifield.on("click", ".js-coral-Multifield-remove", validateCount);
    });
  });

})(jQuery, window, document);
