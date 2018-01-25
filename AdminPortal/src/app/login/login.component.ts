import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
	loggedIn : boolean;
	username :string;
	password : string;

  constructor(private _loginService:LoginService) { 
  	//local storage is sth like cookie but much more better :D
  	if (localStorage.getItem('PortalAdminHasLoggedIn') == '') { 
  		this.loggedIn = false;
  	} else {
  		this.loggedIn = true;
  	}
  }

  ngOnInit() {
  }

}
