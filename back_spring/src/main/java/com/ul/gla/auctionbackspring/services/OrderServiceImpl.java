package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.ArticleRepository;
import com.ul.gla.auctionbackspring.dao.OfferRepository;
import com.ul.gla.auctionbackspring.dao.OrderRepository;
import com.ul.gla.auctionbackspring.dto.CreateOrderDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.entities.Offer;
import com.ul.gla.auctionbackspring.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrderServiceImpl implements OrderService{

    private static final String ORDERSTEPONE = "Order sent";
    private static final String ORDERSTEPTWO = "Order confirmed";
    private static final String ORDERSTEPTHREE = "Order awaiting shipment (not managed)";
    private static final String ORDERSTEPFOUR = "Order awaiting shipment";
    private static final String ORDERSTEPFIVE = "Order shipped";
    private static final long TIMEOFEFFECTIVEPROMOTION = 259200; //it's in second, Dont forget to do the /1000 with the timestamp compared with this

    @Autowired
    private OrderRepository orderDao;
    @Autowired
    private ArticleRepository articleDao;
    @Autowired
    private OfferRepository offerDao;
    @Autowired
    private RMQCommunicationService communicationService;


    @Override
    public Iterable<Order> getOrdersByUsername(String username) {
        return this.orderDao.findAll(username);
    }

    @Override
    public Order getOrderById(long id) {
        return this.orderDao.find(id);
    }

    @Override
    public Order getOrderByArticleId(long articleId) {
        return this.orderDao.findByArticleId(articleId);
    }

    @Override
    public Iterable<Order> getOrdersBySeller(String seller) {
        return this.orderDao.findBySeller(seller);
    }

    @Override
    public Order saveOrder(CreateOrderDto newOrder) {
        //On check si la date de timestamp est inférieur a 3 jour par rapport à aujourd'hui
        //on check si y a une promo effective sur la catégorie du produit
        Article article =  articleDao.find(newOrder.getArticleId());

        if(article.getEndingDate() + TIMEOFEFFECTIVEPROMOTION >= System.currentTimeMillis() / 1000 ){ //If the offer can be effective
            if(articleOfferGotOneCategorieInCommon(article, offerDao.find())){
                //Ofait la reduction sur l'OrderDto
            }
        }
        Order order = new Order(newOrder.getBuyer(), newOrder.getArticleId(), ORDERSTEPONE, newOrder.getFirstname(),
                newOrder.getLastname(), newOrder.getStreetNumber() + " " + newOrder.getStreetName(),
                newOrder.getZipcode(), newOrder.getCity());
        return this.orderDao.save(order);
    }


    /**
     * Compare the categorie of the two
     * @param article
     * @param offer
     * @return true if there is a match
     */
    private boolean articleOfferGotOneCategorieInCommon(Article article, Offer offer){
        String articleCategories = article.getCategories();
        String[] categoriesArr;
        if (articleCategories != null && !articleCategories.equals("")) {
            categoriesArr = articleCategories.split(",", -1);
        }
        else {
            categoriesArr = new String[0];
        }

        return compareCategories(categoriesArr, offer.getCategory());
    }

    /**
     * Compare an array of categorie with a string
     * @param articleCategories the array corresponding an article
     * @param offerCategorie  the offer we want to compare to
     * @return boolean true if there is a match
     */
    private boolean compareCategories(String[] articleCategories, String offerCategorie){
        boolean res = false;
        for(String a : articleCategories){
            if(a.equals(offerCategorie)){
                res = true;
            }
        }
        return res;
    }

    @Override
    public Order updateOrder(Order order) {
        switch (order.getStatus()) {
            case ORDERSTEPONE:
                return this.updateOrderStatusSet(order, ORDERSTEPTWO);
            case ORDERSTEPTWO:
                Order o = this.updateOrderStatusSet(order, ORDERSTEPTHREE);
                this.communicationService.sendOrder(order.getId() + "");
                return o;
            case ORDERSTEPTHREE:
                this.communicationService.sendOrder(order.getId() + "");

        }
        return order;
    }

    @Override
    public void updateOrderFromShippingApplication(long id) {
        Order order = this.orderDao.find(id);
        if (order != null) {
            if (ORDERSTEPFOUR.equals(order.getStatus())) {
                this.updateOrderStatusSet(order, ORDERSTEPFIVE);
            }
        }
    }

    @Override
    public void confirmOrderReceptionFromShippingApplication(long id) {
        Order order = this.orderDao.find(id);
        if (order != null) {
            if (ORDERSTEPTHREE.equals(order.getStatus())) {
                this.updateOrderStatusSet(order, ORDERSTEPFOUR);
            }
        }
    }

    private Order updateOrderStatusSet(Order order, String newStatus) {
        int updated = this.orderDao.update(order.getId(), newStatus);
        if (updated == 0) {
            return null;
        }
        return new Order(order.getId(), order.getBuyer(), order.getArticleId(), newStatus, order.getFirstname(),
                order.getLastname(), order.getStreet(), order.getZipcode(), order.getCity());
    }

}
