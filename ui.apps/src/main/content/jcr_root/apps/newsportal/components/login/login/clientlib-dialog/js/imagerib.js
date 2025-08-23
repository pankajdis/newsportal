(function ($, $document) {
  "use strict";

  $document.on("click", ".cq-dialog-submit", function (e) {
    var images = $("input[name='./images']").closest(".coral-Form-fieldwrapper").find("coral-multifield-item");
    if (images.length !== 7) {
      e.preventDefault();
      Coral.commons.ready(function () {
        Coral.dialog.alert("Please add exactly 7 images.");
      });
    }
  });

})(jQuery, jQuery(document));
