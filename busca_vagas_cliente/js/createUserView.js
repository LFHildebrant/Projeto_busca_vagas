import { UserService } from "./userService.js";

class CreateUserView{
    constructor(){
        this.message = document.getElementById("message");
        this.profile = document.getElementById("profile");
        this.inputName = document.getElementById("name");
        this.inputUsername = document.getElementById("username");
        this.inputPassword = document.getElementById("password");
        this.inputEmail = document.getElementById("email");
        this.inputPhone = document.getElementById("phone");
        this.inputExperience = document.getElementById("experience");
        this.inputEducation = document.getElementById("education");
        this.buttonSubmit = document.getElementById("buttonSubmit");
        this.userForm = document.getElementById("userForm");
        this.buttonDelete = document.getElementById("buttonDelete");

        this.userForm.addEventListener("submit", this.handleSubmitUpdate.bind(this));
    }

    async handleSubmitUpdate(event){
        event.preventDefault();
        const userService = new UserService();
        const fields = ["name", "username", "password", "phone", "email", "experience", "education"];

        const user = {};

        fields.forEach(field => {
            const value = document.getElementById(field).value.trim();
            if (value !== "") {
                user[field] = value;
            }
        });

        try{
            const result = await userService.createUser(user);

            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message + " REDIRECIONANDO....";         
            this.message.classList.remove("hide"); 
            
            setTimeout(() => {
                    window.location.replace("index.html");
                }, 2000)
        } catch (e){
            console.log(e);
            if (e.message) {
            console.log("Detalhes:", e.response);
            }
        }
    } 

}

new CreateUserView();