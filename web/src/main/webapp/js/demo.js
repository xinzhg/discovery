/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Jiayi Liu @ Nov 20, 2013
 */
var form;

function loaded() {
    document.getElementById("password").focus();
}

// encrypt the text
function doEncrypt() {
    // set data
    var plaintext = document.getElementById("entry1").value;
    var password = document.getElementById("password").value;
    var encrypted = document.getElementById("entry2");
    
    if (plaintext === '') { return; }  // no password
    if (password.length === 0) {
        alert("need a password!");
        return;
    }
    ct = sjcl.encrypt(password,plaintext);
    // return result
    encrypted.value = ct;
}
    
// decrypt the text
function doDecrypt() {
    // set data
    var plaintext = document.getElementById("entry1");
    var password = document.getElementById("password").value;
    var encrypted = document.getElementById("entry2").value;
    
    if (encrypted === '') { return; }  // no password
    if (password.length === 0) {
        alert("need a password!");
        return;
    }
    try {
        ct = sjcl.decrypt(password,encrypted);
    } catch(e) {
        alert("Can't decrypt: "+e);
    }
    // return result
    plaintext.value = ct;
}

// load file
function getFile() {
    var fileForUpload = document.getElementById("file").files;
    alert(fileForUpload.var);
}