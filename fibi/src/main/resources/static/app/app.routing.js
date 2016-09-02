"use strict";
var router_1 = require('@angular/router');
var investments_component_1 = require('./investments/investments.component');
var home_component_1 = require('./home/home.component');
var appRoutes = [
    { path: 'home', component: home_component_1.HomeComponent },
    { path: 'investments', component: investments_component_1.InvestmentsComponent },
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    }
];
exports.routing = router_1.RouterModule.forRoot(appRoutes);
//# sourceMappingURL=app.routing.js.map