package com.ming.androidmapbox.marker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.exceptions.InvalidMarkerPositionException;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class CustomMarkerOptions extends BaseMarkerOptions<CustomMarker, CustomMarkerOptions> implements Parcelable {

    public static final Creator<CustomMarkerOptions> CREATOR = new Creator<CustomMarkerOptions>() {
        public CustomMarkerOptions createFromParcel(Parcel in) {
            return new CustomMarkerOptions(in);
        }

        public CustomMarkerOptions[] newArray(int size) {
            return new CustomMarkerOptions[size];
        }
    };

    public CustomMarkerOptions() {
    }

    protected CustomMarkerOptions(Parcel in) {
        this.position((LatLng) in.readParcelable(LatLng.class.getClassLoader()));
        this.snippet(in.readString());
        this.title(in.readString());
        if (in.readByte() != 0) {
            String iconId = in.readString();
            Bitmap iconBitmap = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
            Icon icon = IconFactory.recreate(iconId, iconBitmap);
            this.icon(icon);
        }

    }

    public CustomMarkerOptions getThis() {
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.getPosition(), flags);
        out.writeString(this.getSnippet());
        out.writeString(this.getTitle());
        Icon icon = this.getIcon();
        out.writeByte((byte) (icon != null ? 1 : 0));
        if (icon != null) {
            out.writeString(this.getIcon().getId());
            out.writeParcelable(this.getIcon().getBitmap(), flags);
        }

    }

    public CustomMarker getMarker() {
        if (this.position == null) {
            throw new InvalidMarkerPositionException();
        } else {
            return new CustomMarker(this);
        }
    }

    public LatLng getPosition() {
        return this.position;
    }

    public String getSnippet() {
        return this.snippet;
    }

    public String getTitle() {
        return this.title;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            CustomMarkerOptions marker;
            label59:
            {
                marker = (CustomMarkerOptions) o;
                if (this.getPosition() != null) {
                    if (this.getPosition().equals(marker.getPosition())) {
                        break label59;
                    }
                } else if (marker.getPosition() == null) {
                    break label59;
                }

                return false;
            }

            label52:
            {
                if (this.getSnippet() != null) {
                    if (this.getSnippet().equals(marker.getSnippet())) {
                        break label52;
                    }
                } else if (marker.getSnippet() == null) {
                    break label52;
                }

                return false;
            }

            if (this.getIcon() != null) {
                if (!this.getIcon().equals(marker.getIcon())) {
                    return false;
                }
            } else if (marker.getIcon() != null) {
                return false;
            }

            boolean var10000;
            label77:
            {
                if (this.getTitle() != null) {
                    if (this.getTitle().equals(marker.getTitle())) {
                        break label77;
                    }
                } else if (marker.getTitle() == null) {
                    break label77;
                }

                var10000 = false;
                return var10000;
            }

            var10000 = true;
            return var10000;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.getPosition() != null ? this.getPosition().hashCode() : 0);
        result = 31 * result + (this.getSnippet() != null ? this.getSnippet().hashCode() : 0);
        result = 31 * result + (this.getIcon() != null ? this.getIcon().hashCode() : 0);
        result = 31 * result + (this.getTitle() != null ? this.getTitle().hashCode() : 0);
        return result;
    }
}

