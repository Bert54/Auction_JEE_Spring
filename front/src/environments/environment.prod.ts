export const environment = {
  production: true,
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
      updateOrder: '/auctions/orders/received',
    }
  }
};
