// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backend: {
    protocol: 'http',
    host: '0.0.0.0',
    port: '8080',
    endpoints: {
      users: '/auctions/users',
      signup: '/auctions/users/signup',
      login: '/auctions/users/login',
      allArticles: '/auctions/articles',
      oneArticle: '/auctions/articles/:id',
      addOneArticle: '/auctions/articles/add',
      deleteOneArticle: '/auctions/articles/delete/:id',
      searchArticles: '/auctions/articles/search',
      newBid: '/auctions/articles/bid',
      userBids: '/auctions/articles/userbids',
      offers: '/auctions/miscs/offers',
      buyableArticles: '/auctions/articles/buyable',
      allOrders: '/auctions/orders',
      oneOrder: '/auctions/orders/:id',
      userinfo: '/auctions/users/info',
      receivedOrder: '/auctions/orders/received',
      updateOrder: '/auctions/orders/received/update/:id',
    }
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
