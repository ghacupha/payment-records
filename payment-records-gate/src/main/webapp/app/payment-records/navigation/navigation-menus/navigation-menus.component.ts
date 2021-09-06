import {Component, OnInit} from "@angular/core";
import {LoginService} from "app/core/login/login.service";
import {AccountService} from "app/core/auth/account.service";
import {LoginModalService} from "app/core/login/login-modal.service";
import {ProfileService} from "app/layouts/profiles/profile.service";
import {Router} from "@angular/router";
import {VERSION} from "app/app.constants";

@Component({
  selector: "jhi-navigation-menus-nav",
  templateUrl: "./navigation-menus.component.html",
  styleUrls: ["../payment-navigation.scss"]
})
export class NavigationMenusComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  swaggerEnabled?: boolean;
  version: string;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
