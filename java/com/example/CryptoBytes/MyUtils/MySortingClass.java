package com.example.CryptoBytes.MyUtils;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.DataMain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MySortingClass {
    public static void sortList(List<DataMain> currencyList, int number) {
        switch (number) {
            // Name A-Z
            case 0:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                break;
            // Market Cap
            case 1:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        return lhs.getCmcRank() < rhs.getCmcRank() ? -1 : lhs.getCmcRank() > rhs.getCmcRank() ? +1 : 0;
                    }
                });
                break;
            // Price
            case 2:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPrice() == null && rhs.getQuote().getUSD().getPrice() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPrice() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPrice() == null) {
                            return -1;
                        }
                        float comp = (float) ((rhs.getQuote().getUSD().getPrice()) - (lhs.getQuote().getUSD().getPrice()));
                        return floatComp(comp);
                    }
                });
                break;
            // Volume 24h
            case 3:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getVolume24h() == null && rhs.getQuote().getUSD().getVolume24h() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getVolume24h() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getVolume24h() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getVolume24h() - lhs.getQuote().getUSD().getVolume24h());
                        return floatComp(comp);
                    }
                });
                break;
            // Change 1h
            case 4:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPercentChange1h() == null && rhs.getQuote().getUSD().getPercentChange1h() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPercentChange1h() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPercentChange1h() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPercentChange1h() -  lhs.getQuote().getUSD().getPercentChange1h());
                        return floatComp(comp);
                    }
                });
                break;
            // Change 24h
            case 5:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPercentChange24h() == null && rhs.getQuote().getUSD().getPercentChange24h() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPercentChange24h() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPercentChange24h() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPercentChange24h() -  lhs.getQuote().getUSD().getPercentChange24h());
                        return floatComp(comp);
                    }
                });
                break;
            // Change 7d
            case 6:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPercentChange7d() == null && rhs.getQuote().getUSD().getPercentChange7d() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPercentChange7d() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPercentChange7d() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPercentChange7d() -  lhs.getQuote().getUSD().getPercentChange7d());
                        return floatComp(comp);
                    }
                });
                break;
            // Name Z-A
            case 7:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        return rhs.getName().compareTo(lhs.getName());
                    }
                });
                break;
            // Market Cap LH
            case 8:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        return rhs.getCmcRank() < lhs.getCmcRank() ? -1 : rhs.getCmcRank() > lhs.getCmcRank() ? +1 : 0;
                    }
                });
                break;
            // Price LH
            case 9:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPrice() == null && rhs.getQuote().getUSD().getPrice() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPrice() == null || rhs.getQuote().getUSD().getPrice() == null) {
                            return rhs.getQuote().getUSD().getPrice() < lhs.getQuote().getUSD().getPrice() ? -1 : rhs.getQuote().getUSD().getPrice() > lhs.getQuote().getUSD().getPrice() ? +1 : 0;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPrice() - lhs.getQuote().getUSD().getPrice());
                        return floatCompLH(comp);
                    }
                });
                break;
            // Volume 24h LH
            case 10:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getVolume24h() == null && rhs.getQuote().getUSD().getVolume24h() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getVolume24h()== null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getVolume24h() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getVolume24h() - lhs.getQuote().getUSD().getVolume24h());
                        return floatCompLH(comp);
                    }
                });
                break;
            // Change 1h LH
            case 11:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPercentChange1h() == null && rhs.getQuote().getUSD().getPercentChange1h() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPercentChange1h() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPercentChange1h() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPercentChange1h() - lhs.getQuote().getUSD().getPercentChange1h());
                        return floatCompLH(comp);
                    }
                });
                break;
            // Change 24h LH
            case 12:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPercentChange24h() == null && rhs.getQuote().getUSD().getPercentChange24h() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPercentChange24h() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPercentChange24h() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPercentChange24h() - lhs.getQuote().getUSD().getPercentChange24h());
                        return floatCompLH(comp);
                    }
                });
                break;
            // Change 7d LH
            case 13:
                Collections.sort(currencyList, new Comparator<DataMain>() {
                    @Override
                    public int compare(DataMain lhs, DataMain rhs) {
                        if (lhs.getQuote().getUSD().getPercentChange7d() == null && rhs.getQuote().getUSD().getPercentChange7d() == null) {
                            return 0;
                        }
                        if (lhs.getQuote().getUSD().getPercentChange7d() == null) {
                            return 1;
                        }
                        if (rhs.getQuote().getUSD().getPercentChange7d() == null) {
                            return -1;
                        }
                        float comp = (float) (rhs.getQuote().getUSD().getPercentChange7d() - lhs.getQuote().getUSD().getPercentChange7d());
                        return floatCompLH(comp);
                    }
                });
                break;
        }
    }

    private static int floatComp(float f) {
        if (f == 0) {
            return 0;
        } else if (f < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    private static int floatCompLH(float f) {
        if (f == 0) {
            return 0;
        } else if (f < 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
