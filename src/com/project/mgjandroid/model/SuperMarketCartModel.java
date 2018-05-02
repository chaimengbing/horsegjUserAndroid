package com.project.mgjandroid.model;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/10/14.
 */

public class SuperMarketCartModel {

    private static SuperMarketCartModel mInstance;

    private SuperMarketCartBean superMarketCartBean = null;

    private boolean hasChange;

    public SuperMarketCartModel() {

    }

    public static SuperMarketCartModel getInstance() {
        if (mInstance == null) {
            synchronized (SuperMarketCartModel.class) {
                if (mInstance == null) {
                    mInstance = new SuperMarketCartModel();
                }
            }
        }
        return mInstance;
    }

    public void initData() {
        if (superMarketCartBean == null) {
            String data = PreferenceUtils.getSuperMarketCartData(App.getInstance());
            superMarketCartBean = JSON.parseObject(data, SuperMarketCartModel.SuperMarketCartBean.class);
        }
    }

    public SuperMarketCartBean getSuperMarketCartBean() {
        return superMarketCartBean;
    }

    public void setSuperMarketCartBean(SuperMarketCartBean superMarketCartBean) {
        this.superMarketCartBean = superMarketCartBean;
    }

    public boolean isHasChange() {
        return hasChange;
    }

    public void setHasChange(boolean hasChange) {
        this.hasChange = hasChange;
    }


    public static class SuperMarketCartBean extends Entity {

        private long merchantId = -1;

        private long agentId = -1;

        private ArrayList<CartGoods> cartGoods = new ArrayList<>();

        public long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(long merchantId) {
            this.merchantId = merchantId;
        }

        public long getAgentId() {
            return agentId;
        }

        public void setAgentId(long agentId) {
            this.agentId = agentId;
        }

        public ArrayList<CartGoods> getCartGoods() {
            return cartGoods;
        }

        public void setCartGoods(ArrayList<CartGoods> cartGoods) {
            this.cartGoods = cartGoods;
        }

        public String getTotalCount() {
            int count = 0;
            for (CartGoods goods : cartGoods) {
                count += goods.getButCount();
            }
//            MLog.e("cart_count------------->" + count);
            if (count != 0) return count + "";
            return "";
        }

        public void addGoods(long goodsId, int count) {
            boolean hasThisGoods = false;
            for (CartGoods goods : cartGoods) {
                if (goods.getGoodsId() == goodsId) {
                    goods.setButCount(goods.getButCount() + count);
                    hasThisGoods = true;
                    break;
                }
            }
            if (!hasThisGoods) {
                CartGoods goods = new CartGoods();
                goods.setButCount(count);
                goods.setGoodsId(goodsId);
                cartGoods.add(goods);
            }
            save2SharePreference();
        }

        public void minusGoods(long goodsId, int count) {
            for (CartGoods goods : cartGoods) {
                if (goods.getGoodsId() == goodsId) {
                    if (goods.getButCount() - count == 0) {
                        cartGoods.remove(goods);
                    } else {
                        goods.setButCount(goods.getButCount() - count);
                    }
                    break;
                }
            }
            save2SharePreference();
        }

        public void deleteGoods(long goodsId) {
            for (CartGoods goods : cartGoods) {
                if (goods.getGoodsId() == goodsId) {
                    cartGoods.remove(goods);
                    break;
                }
            }
            save2SharePreference();
        }

        public void clearCart() {
            cartGoods.clear();
            save2SharePreference();
        }

        private void save2SharePreference() {
            String data = JSON.toJSONString(this);
            PreferenceUtils.saveSuperMarketCartData(data, App.getInstance());
        }
    }

    public static class CartGoods extends Entity {

        private long goodsId;

        private int butCount;

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public int getButCount() {
            return butCount;
        }

        public void setButCount(int butCount) {
            this.butCount = butCount;
        }
    }
}
