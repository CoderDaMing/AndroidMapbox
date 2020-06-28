package com.ming.androidmapbox.symbol.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * https://docs.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson
 */
public class MapGeoJson {
    /**
     * type : FeatureCollection
     * crs : {"type":"name","properties":{"name":"urn:ogc:def:crs:OGC:1.3:CRS84"}}
     * features : [{"type":"Feature","properties":{"id":"ak16994521","mag":2.3,"time":1507425650893,"felt":null,"tsunami":0},"geometry":{"type":"Point","coordinates":[-151.5129,63.1016,0]}},{"type":"Feature","properties":{"id":"ak16994519","mag":1.7,"time":1507425289659,"felt":null,"tsunami":0},"geometry":{"type":"Point","coordinates":[-150.4048,63.1224,105.5]}},{"type":"Feature","properties":{"id":"ak16994517","mag":1.6,"time":1507424832518,"felt":null,"tsunami":0},"geometry":{"type":"Point","coordinates":[-151.3597,63.0781,0]}}]
     */

    private String type = "FeatureCollection";
    private CrsBean crs;
    private List<FeaturesBean> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CrsBean getCrs() {
        return crs;
    }

    public void setCrs(CrsBean crs) {
        this.crs = crs;
    }

    public List<FeaturesBean> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesBean> features) {
        this.features = features;
    }

    public static class CrsBean {
        /**
         * type : name
         * properties : {"name":"urn:ogc:def:crs:OGC:1.3:CRS84"}
         */

        private String type = "name";
        private PropertiesBean properties;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public static class PropertiesBean {
            /**
             * name : urn:ogc:def:crs:OGC:1.3:CRS84
             */

            private String name = "urn:ogc:def:crs:OGC:1.3:CRS84";

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class FeaturesBean {
        /**
         * type : Feature
         * properties : {"type":0,"id":"ak16994521","mag":2.3,"time":1507425650893,"felt":null,"tsunami":0}
         * geometry : {"type":"Point","coordinates":[-151.5129,63.1016,0]}
         */

        private String type = "Feature";
        private PropertiesBeanX properties;
        private GeometryBean geometry;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PropertiesBeanX getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBeanX properties) {
            this.properties = properties;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public static class PropertiesBeanX {
            /**
             * id : ak16994521
             * mag : 2.3
             * time : 1507425650893
             * felt : null
             * tsunami : 0
             */
            private String id;
            private double mag;
            private long time;
            private Object felt;
            private int tsunami;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getMag() {
                return mag;
            }

            public void setMag(double mag) {
                this.mag = mag;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public Object getFelt() {
                return felt;
            }

            public void setFelt(Object felt) {
                this.felt = felt;
            }

            public int getTsunami() {
                return tsunami;
            }

            public void setTsunami(int tsunami) {
                this.tsunami = tsunami;
            }
        }

        public static class GeometryBean {
            /**
             * type : Point
             * coordinates : [-151.5129,63.1016,0]
             */

            private String type = "Point";
            private List<Double> coordinates;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }
        }
    }

    /**
     * 生成Json字符串
     */
    public String crateJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}


