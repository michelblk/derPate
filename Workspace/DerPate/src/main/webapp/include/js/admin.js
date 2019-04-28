function checkPassword(form) { 
	password = form.password.value; 
	passwordconfirm = form.password_confirm.value; 
	
	// If password not entered 
    if (password == '') 
        alert ("Please enter Password"); 
          
    // If confirm password not entered 
    else if (password_confirm == '') 
        alert ("Please enter confirm password"); 
          
    // If Not same return False.     
    else if (password != password_confirm) { 
        alert ("\nPassword did not match: Please try again...") 
                    return false; 
                } 
  
    // If same return True. 
    else{ 
        alert("Password Match: Set new password") 
        return true; 
    } 
} 