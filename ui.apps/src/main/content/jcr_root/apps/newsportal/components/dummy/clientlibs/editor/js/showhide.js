
    $(document).ready(function() {
        // Function to show/hide the target text field
        function toggleTargetField() {
            // Get the selected value from the pathfield
            var selectedPath = $('.cq-dialog-pathfield-showhide').val();
            
            // Check if the selected path matches the condition to show the target field
            if (selectedPath === '/content/newsportal/us/en') { // Replace 'yourConditionPath' with the actual path value
                $('.list-option-listfrom-showhide-target').show();
            } else {
                $('.list-option-listfrom-showhide-target').hide();
            }
        }

        // Initial check to set visibility on page load
        toggleTargetField();

        // Attach change event listener to the pathfield
        $('.cq-dialog-pathfield-showhide').on('change', function() {
            toggleTargetField();
        });

        // Attach a listener for cases where the pathfield value changes through other means (e.g., selections in the dialog)
        // The pathfield might use a different event to signal changes
        $('.cq-dialog-pathfield-showhide').on('coral-pathfield:change', function() {
            toggleTargetField();
        });
    });
