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
      createArticle: '/auctions/articles/add',
      deleteOneArticle: '/auctions/articles/delete/:id',
    }
  }
};
