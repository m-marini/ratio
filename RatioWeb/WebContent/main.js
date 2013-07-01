alert("Ciao");

	function submitDefaultForm(evt) {
		var charCode = (evt.charCode) ? evt.charCode : ((evt.which) ? evt.which : evt.keyCode);
		if (charCode == 13 || charCode == 3) {
			document.forms['mainForm']['mainForm:run'].click();
			return false;
		}
		return true;
	}