import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
 
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	loggedIn : boolean;

  constructor(private _loginService:LoginService , private _router:Router) { 
  	if (localStorage.getItem('PortalAdminHasLoggedIn')=='') { 
  		this.loggedIn = false;
  	} else {
  		this.loggedIn = true;
  	}
  }

  logout(){
  	this._loginService.logout().subscribe(
  			res => {
  				localStorage.setItem('PortalAdminHasLoggedIn','');
  			},
  			err => console.log(err)
  		);
  	location.reload();
  	this._router.navigate(['/login']);
  }
  getDisplay(){
  	if (!this.loggedIn) { 
  		return 'none';
  	} else {
  		return '';
  	}
  }

  ngOnInit() {
  }

}
