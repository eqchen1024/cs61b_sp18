import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public Rasterer() {
        boolean query_success = false;

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */

    public int getDepth(double userRequiredLonDPP) {
        double accurateDepth = Math.log(
                (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) /
                        (MapServer.TILE_SIZE *  userRequiredLonDPP))
                / Math.log(2);
        int depth = Math.min((int) Math.ceil(accurateDepth),7);
        return depth;
    }

    public double getGridWidth(int depth,double origin, double end) {
        double gridNum = Math.pow(2, depth);
        double scale = end - origin;
        double gridAbsWidth = scale / gridNum;
        return gridAbsWidth;
    }
    public int getPosIndex(int depth, double origin, double end, double pos) {
        double scale = end - origin;
        double absDiff = pos - origin;
        double relDiff = absDiff / scale;
        double gridNum = Math.pow(2, depth);
        double gridRelWidth = 1 / gridNum;
        int posIndex = (int) Math.floor(relDiff / gridRelWidth);
        return posIndex;
    }

    public int getXIndex(int depth, double longitude) {
        return getPosIndex(depth, MapServer.ROOT_ULLON, MapServer.ROOT_LRLON, longitude);
    }

    public int getYIndex(int depth, double latitude) {
        return getPosIndex(depth, MapServer.ROOT_ULLAT, MapServer.ROOT_LRLAT, latitude);
    }

    public double getPosByIndex(int index,double origin, double step_width){
        return origin + index * step_width;
    }

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // 1. handle errors
        // 2. get params
        double queryBoxLrLon = params.get("lrlon");
        double queryBoxUlLon = params.get("ullon");
        double queryBoxLrLat = params.get("lrlat");
        double queryBoxUlLat = params.get("ullat");
        double userViewWidth= params.get("w");
        double userViewHeight = params.get("h");
        // 3. calculate LPP and decide depth
        double userRequiredLonDPP = (queryBoxLrLon - queryBoxUlLon) / userViewWidth;
        int depth = getDepth(userRequiredLonDPP);
        // 4. calculate image index
        int leftXIndex = getXIndex(depth,queryBoxUlLon);
        int rightXIndex = getXIndex(depth,queryBoxLrLon);
        int upYIndex = getYIndex(depth,queryBoxUlLat);
        int lowYIndex = getYIndex(depth,queryBoxLrLat);
        String[][] imageList = new String[lowYIndex - upYIndex + 1][rightXIndex - leftXIndex + 1];
        for (int j = upYIndex; j <= lowYIndex; j++) {
            for (int i = leftXIndex; i <= rightXIndex; i++) {
                imageList[j - upYIndex][i - leftXIndex] = String.format("d%s_x%s_y%s.png",depth,i,j);
                System.out.println(imageList[j - upYIndex][i - leftXIndex]);
            }
        }
        // 5. get pic lat long bound
        double gridXWidth = getGridWidth(depth,MapServer.ROOT_ULLON,MapServer.ROOT_LRLON);
        double gridYWidth = getGridWidth(depth,MapServer.ROOT_ULLAT,MapServer.ROOT_LRLAT);
        double PicUlLon = getPosByIndex(leftXIndex,MapServer.ROOT_ULLON,gridXWidth);
        double PicLrLon = getPosByIndex(rightXIndex + 1,MapServer.ROOT_ULLON,gridXWidth); // + 1 for right bound
        double PicUlLat = getPosByIndex(upYIndex,MapServer.ROOT_ULLAT,gridYWidth);
        double PicLrLat = getPosByIndex(lowYIndex + 1,MapServer.ROOT_ULLAT,gridYWidth);

        Map<String, Object> results = new HashMap<>();
        results.put("render_grid",imageList);
        results.put("depth",depth);
        results.put("raster_ul_lon",PicUlLon);
        results.put("raster_lr_lon",PicLrLon);
        results.put("raster_ul_lat",PicUlLat);
        results.put("raster_lr_lat",PicLrLat);
        results.put("query_success",true);
        return results;
    }

}
