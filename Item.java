public class Item {
   private String description;
   private Double weigth;

   public Item(String description, Double weight){
      this.description = description;
      this.weigth = weight;
   }

   public String getDescriptionItem(){
      return description;
   }

   public Double getWeigth(){
      return weigth;
   }
}
