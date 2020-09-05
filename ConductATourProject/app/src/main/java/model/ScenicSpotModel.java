package model;

/**
 * 景点模型
 */
public class ScenicSpotModel {
    private String ScenicId;
    private String scenicName;
    private String scenicPic;
    private String scenicDes;

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getScenicPic() {
        return scenicPic;
    }

    public void setScenicPic(String scenicPic) {
        this.scenicPic = scenicPic;
    }

    public String getScenicDes() {
        return scenicDes;
    }

    public void setScenicDes(String scenicDes) {
        this.scenicDes = scenicDes;
    }

    public void setScenicId(String scenicId) {
        ScenicId = scenicId;
    }
}
