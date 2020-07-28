package com.ming.androidmapbox.marker;

import com.ming.androidmapbox.MapActivity;
import com.ming.androidmapbox.util.ToastUtil;

/**
 * MarkerView已过时被mapbox在高版本删除（5.1.3左右存在 ）
 */
public class CustomMarkerTwoActivity extends MapActivity {
    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        ToastUtil.show("MarkerView已过时被mapbox在高版本删除（5.1.3左右存在 ）");
//        //设置管理MarkerView
//        MarkerViewManager markerViewManager = mapboxMap.getMarkerViewManager();
//        MyMarkerViewAdapter markerViewAdapter = new MyMarkerViewAdapter(MyApplication.getInstance();
//        markerViewManager.addMarkerViewAdapter(markerViewAdapter);
//
//        MyMarkerViewOptions overlayOptions = new MyMarkerViewOptions();
//        overlayOptions.position(mapboxMap.getCameraPosition().target);
//        MyMarkerView testMarker = (MyMarkerView) mapboxMap.addMarker(overlayOptions);
//
//        mapBoxMap.selectMarker(testMarker);//设置marker被选中状态放大
//        mapBoxMap.deselectMarker(testMarker);//设置marker非选中状态缩小


    }

//    public class MyMarkerView extends MarkerView {
//        /**
//         * 使用MarkerView的构建器创建MarkerView的实例
//         *
//         * @param baseMarkerViewOptions baseMarkerView选项用于构建MarkerView的构建器
//         *      
//         */
//        public MyMarkerView(BaseMarkerViewOptions baseMarkerViewOptions) {
//            super(baseMarkerViewOptions);
//        }
//    }

//    public class MyMarkerViewOptions extends BaseMarkerViewOptions<MyMarkerView, MyMarkerViewOptions> {
//        public MyMarkerViewOptions() {
//        }
//
//        private MyMarkerViewOptions(Parcel in) {
//            position((LatLng) in.readParcelable(LatLng.class.getClassLoader()));
//            snippet(in.readString());
//            title(in.readString());
//            flat(in.readByte() != 0);
//            anchor(in.readFloat(), in.readFloat());
//            selected = in.readByte() != 0;
//            rotation(in.readFloat());
//            if (in.readByte() != 0) {
//                // this means we have an icon
//                String iconId = in.readString();
//                Bitmap iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
//                Icon icon = IconFactory.recreate(iconId, iconBitmap);
//                icon(icon);
//            }
//        }
//
//        @Override
//        public MyMarkerViewOptions getThis() {
//            return this;
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            out.writeParcelable(getPosition(), flags);
//            out.writeString(getSnippet());
//            out.writeString(getTitle());
//            out.writeByte((byte) (isFlat() ? 1 : 0));
//            out.writeFloat(getAnchorU());
//            out.writeFloat(getAnchorV());
//            out.writeFloat(getInfoWindowAnchorU());
//            out.writeFloat(getInfoWindowAnchorV());
//            out.writeByte((byte) (selected ? 1 : 0));
//            out.writeFloat(getRotation());
//            Icon icon = getIcon();
//            out.writeByte((byte) (icon != null ? 1 : 0));
//            if (icon != null) {
//                out.writeString(getIcon().getId());
//                out.writeParcelable(getIcon().getBitmap(), flags);
//            }
//        }
//
//        @Override
//        public MyMarkerView getMarker() {
//            return new MyMarkerView(this);
//        }
//
//        public static final Parcelable.Creator<MyMarkerViewOptions> CREATOR =
//                new Parcelable.Creator<MyMarkerViewOptions>() {
//                    public MyMarkerViewOptions createFromParcel(Parcel in) {
//                        return new MyMarkerViewOptions(in);
//                    }
//
//                    public MyMarkerViewOptions[] newArray(int size) {
//                        return new MyMarkerViewOptions[size];
//                    }
//                };
//    }

//    public class MyMarkerViewAdapter extends MapboxMap.MarkerViewAdapter<MyMarkerView> {
//        private LayoutInflater inflater;
//
//        /**
//         * Create an instance of MarkerViewAdapter.
//         *
//         * @param context the context associated to a MapView
//         */
//        public MyMarkerViewAdapter(Context context) {
//            super(context);
//            this.inflater = LayoutInflater.from(context);
//        }
//
//        @Nullable
//        @Override
//        public View getView(@NonNull MyMarkerView marker, @Nullable View convertView, @NonNull ViewGroup parent) {
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                viewHolder = new ViewHolder();
//                convertView = inflater.inflate(R.layout.layout_marker_view, parent, false);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//            return convertView;
//        }
//
//        @Override
//        public boolean onSelect(@NonNull MyMarkerView marker, @NonNull View convertView, boolean reselectionFromRecycling) {
//            LogUtils.d("MarkerViewAdapter onSelect");
//            //设置大小
//            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.marker_icon);
//            iconImageView.setImageResource(R.drawable.a_big);
//            return super.onSelect(marker, convertView, reselectionFromRecycling);
//        }
//
//        @Override
//        public void onDeselect(@NonNull MyMarkerView marker, @NonNull View convertView) {
//            LogUtils.d("MarkerViewAdapter onDeselect");
//            //设置大小
//            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.marker_icon);
//            iconImageView.setImageResource(R.drawable.a_normal);
//        }
//
//
//        private static class ViewHolder {
//            ImageView iconImageView;
//        }
//    }
}
