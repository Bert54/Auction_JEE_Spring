export interface Article {
  id?: string;
  name: string;
  description?: string;
  startingPrice: number;
  currentPrice?: number;
  categories: string;
  endingDate: number;
  seller?: string;
  lastBidder?: string;
}
