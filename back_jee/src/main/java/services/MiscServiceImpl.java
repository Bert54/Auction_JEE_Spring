package services;

import dao.OfferDao;
import data.Categories;
import entities.Offer;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Random;

@Stateless
public class MiscServiceImpl implements MiscService {

    private static final int MINREBATE = 5;
    private static final int MAXREBATE = 25;

    @Inject
    private OfferDao offerdao;

    @Schedule(hour="0", minute = "0")
    public void generateNewOffer() {
        this.offerdao.delete();
        String[] categories = Categories.getInstance().getCategories();
        int numCategories = categories.length;
        int selected = new Random().nextInt(numCategories);
        int rebate = new Random().nextInt((MAXREBATE - MINREBATE) + 1) + MINREBATE;
        this.offerdao.save(new Offer(categories[selected], rebate));
    }

    @Override
    public Offer getCurrentOffer() {
        return this.offerdao.find();
    }
}
