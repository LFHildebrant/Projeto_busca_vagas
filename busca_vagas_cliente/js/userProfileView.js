import { Config } from './main.js';
import {UserService} from './userService.js';

class UserProfileView{
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

        this.fillPlaceholders();

        this.userForm.addEventListener("submit", this.handleSubmitUpdate.bind(this));

        this.buttonDelete.addEventListener("click", this.handleSubmitDelete.bind(this));

        this.message.classList.add("hide");
        this.profile.classList.remove("hide");
    }
    
    async handleSubmitUpdate(event){
        event.preventDefault();
        const userService = new UserService();
        let id = Config.getId(localStorage.getItem("token"));
        const fields = ["name", "password", "phone", "email", "experience", "education"];
        const user = {};

        fields.forEach(field => {
            const value = document.getElementById(field).value.trim();
            user[field] = value;
            
        });
        try{
            const result = await userService.updateUser(id, localStorage.getItem("token"), user);
            
            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message;         
            this.message.classList.remove("hide"); 
            
            setTimeout(() => {
                    this.message.classList.add("hide");
                }, 9000)
           
        } catch (e){
            console.log(e);
            if (e.message) {
            console.log("Detalhes:", e.response);
            }
        }
    }

    async handleSubmitDelete(event){
        event.preventDefault();
        const userService = new UserService();
        let id = Config.getId(localStorage.getItem("token"));
        try{
            const result = await userService.deleteUSer(id, localStorage.getItem("token"));

            this.message.style.color = "green";
            this.message.textContent = "Sucesso! -> " + result.message;         
            this.message.classList.remove("hide");     

            setTimeout(() => {
                    window.location.replace("index.html");
                }, 1000)

        } catch (e){
            console.log(e);
            if (e.message) {
            console.log("Detalhes:", e.response);
            }
        }
    }

    async fillPlaceholders(){
        const userService = new UserService();
        try{
            const dataUser = await userService.getAllDataUser(localStorage.getItem("token"), localStorage.getItem("id"));

            this.inputName.value = dataUser.name;
            this.inputUsername.value = dataUser.username;
            this.inputPassword.password = dataUser.password;
            this.inputEmail.value = dataUser.email;
            this.inputPhone.value = dataUser.phone;
            this.inputExperience.value = dataUser.experience;
            this.inputEducation.value = dataUser.education;

            this.inputUsername.classList.add("disable");
            
        } catch (e){
            console.log(e);
        }
        
    }
}

new UserProfileView();