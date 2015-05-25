Confirm = Class.create({
        
    initialize: function(elementId, message) {
        this.message = message;
        Event.observe($(elementId), 'click', this.doConfirm.bindAsEventListener(this));
    },
    
    doConfirm: function(e) {
        
        // Pop up a javascript Confirm Box (see http://www.w3schools.com/js/js_popup.asp)
        
        if (!confirm(this.message)) {
                e.stop();
        }
    }
        
})

// Extend the Tapestry.Initializer with a static method that instantiates a Confirm.

Tapestry.Initializer.confirm = function(spec) {
    new Confirm(spec.elementId, spec.message);
}