import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AccountService } from '../services';

@Component({ templateUrl: 'container.component.html' })
export class ContainerComponent {
    constructor(
        private router: Router,
        private accountService: AccountService
    ) {
        // redirect to home if already logged in
        if (this.accountService.userValue) {
            this.router.navigate(['/']);
        }
    }
}