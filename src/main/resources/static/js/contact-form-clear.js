const elements = document.getElementsByTagName("input");
for (let i = 0; i < elements.length; i++) {
    if (elements[i].type === "submit")
        continue;
    elements[i].value = "";
}

const textarea = document.getElementsByTagName("textarea");
textarea.value = "";

alert("Thank you. Your message has been received and I will get back to you shortly.")