package com.ming.androidmapbox.layers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ming.androidmapbox.R;

public class SymbolLayerActivity extends AppCompatActivity {
    private static final String SYMBOL_SOURCE_ID = "symbol-source-id";
    private static final String SYMBOL_LAYER_ID = "symbol-layer-id";
    private static final String SYMBOL_IMAGE_ID = "symbol-image-id";

    private MapView mapView;
    private GeoJsonSource geoJsonSource;
    private FeatureCollection featureCollection;
    private Feature feature;
    private SymbolLayer symbolLayer;

    private ValueAnimator animatorSize;
    private ValueAnimator animatorPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_layer);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
    }

    private void initMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        initSymbolLayer(style);
                        addNormalSymbolLayer(style);

                        findViewById(R.id.btn_size_animation).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (animatorSize != null && animatorSize.isStarted()) {
                                    animatorSize.cancel();
                                }
                                Float value = symbolLayer.getIconSize().getValue();
                                if (value != null && value > 1f){
                                    animatorSize = ValueAnimator.ofFloat(2f, 1f);
                                }else {
                                    animatorSize = ValueAnimator.ofFloat(1f, 2f);
                                }
                                animatorSize.setDuration(3000);
                                animatorSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        float animatedValue = (float) valueAnimator.getAnimatedValue();
                                        symbolLayer.setProperties(PropertyFactory.iconSize(animatedValue));
                                    }
                                });
                                animatorSize.start();
                            }
                        });

                        findViewById(R.id.btn_position_animation).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Point point = (Point) feature.geometry();
                                LatLng currentPosition = new LatLng(point.latitude(), point.longitude());
                                LatLng target = mapboxMap.getCameraPosition().target;
                                if (currentPosition.distanceTo(target) < 2){
                                    target = new LatLng(38.875,-77.03);
                                }

                                if (animatorPosition != null && animatorPosition.isStarted()) {
                                    animatorPosition.cancel();
                                }
                                animatorPosition = ObjectAnimator
                                        .ofObject(AnimalCons.LATLNG_EVALUATOR, currentPosition, target)
                                        .setDuration(3000);
                                animatorPosition.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        LatLng animatedPosition = (LatLng) animation.getAnimatedValue();
                                        //更新位置
                                        feature = Feature.fromGeometry(Point.fromLngLat(animatedPosition.getLongitude(), animatedPosition.getLatitude()));
                                        featureCollection = FeatureCollection.fromFeatures(new Feature[]{feature});
                                        geoJsonSource.setGeoJson(featureCollection);
                                    }
                                });
                                animatorPosition.start();
                            }
                        });
                    }
                });
            }
        });
    }

    private void initSymbolLayer(Style style) {
        if (style == null) return;
        //source
        geoJsonSource = new GeoJsonSource(SYMBOL_SOURCE_ID, new GeoJsonOptions().withLineMetrics(true));
        style.addSource(geoJsonSource);
        //image
        style.addImage(SYMBOL_IMAGE_ID, getResources().getDrawable(R.drawable.mapbox_marker_icon_default));
        //Layer
        symbolLayer = new SymbolLayer(SYMBOL_LAYER_ID, SYMBOL_SOURCE_ID);
        style.addLayer(symbolLayer);
    }

    private void addNormalSymbolLayer(Style style) {
        symbolLayer.setProperties(
                /*
                 * icon-allow-overlap
                 * Layout property. Optional boolean. Defaults to false. Requires icon-image.
                 * If true, the icon will be visible even if it collides with other previously drawn symbols.
                 */
                PropertyFactory.iconAllowOverlap(true),
                /*
                 * icon-anchor
                 * Layout property. Optional enum. One of "center", "left", "right", "top", "bottom", "top-left", "top-right", "bottom-left", "bottom-right". Defaults to "center". Requires icon-image.
                 * Part of the icon placed closest to the anchor.
                 *
                 * "center":
                 * The center of the icon is placed closest to the anchor.
                 *
                 * "left":
                 * The left side of the icon is placed closest to the anchor.
                 *
                 * "right":
                 * The right side of the icon is placed closest to the anchor.
                 *
                 * "top":
                 * The top of the icon is placed closest to the anchor.
                 *
                 * "bottom":
                 * The bottom of the icon is placed closest to the anchor.
                 *
                 * "top-left":
                 * The top left corner of the icon is placed closest to the anchor.
                 *
                 * "top-right":
                 * The top right corner of the icon is placed closest to the anchor.
                 *
                 * "bottom-left":
                 * The bottom left corner of the icon is placed closest to the anchor.
                 *
                 * "bottom-right":
                 * The bottom right corner of the icon is placed closest to the anchor.
                 */
                PropertyFactory.iconAnchor(Property.ICON_ANCHOR_CENTER),
                /*
                 *icon-color
                 * Paint property. Optional color. Defaults to "#000000". Requires icon-image. Supports feature-state and interpolateexpressions. Transitionable.
                 * The color of the icon. This can only be used with sdf icons.
                 */
                PropertyFactory.iconColor(Color.parseColor("#000000")),
                /*
                 * icon-halo-blur
                 * Paint property. Optional number greater than or equal to 0. Units in pixels. Defaults to 0. Requires icon-image. Supports feature-state and interpolateexpressions. Transitionable.
                 * Fade out the halo towards the outside.
                 */
                PropertyFactory.iconHaloBlur(0f),
                /*
                 *icon-halo-width
                 * Paint property. Optional number greater than or equal to 0. Units in pixels. Defaults to 0. Requires icon-image. Supports feature-state and interpolateexpressions. Transitionable.
                 * Distance of halo to the icon outline.
                 */
                PropertyFactory.iconHaloWidth(0f),
                /*
                 *icon-ignore-placement
                 * Layout property. Optional boolean. Defaults to false. Requires icon-image.
                 * If true, other symbols can be visible even if they collide with the icon.
                 */
                PropertyFactory.iconIgnorePlacement(true),
                /*
                 *icon-image
                 * Layout property. Optional resolvedImage.
                 * Name of image in sprite to use for drawing an image background.
                 */
                PropertyFactory.iconImage(SYMBOL_IMAGE_ID),
                /*
                 *icon-keep-upright
                 * Layout property. Optional boolean. Defaults to false. Requires icon-image. Requires icon-rotation-alignment to be "map". Requires symbol-placement to be "line", or "line-center".
                 * If true, the icon may be flipped to prevent it from being rendered upside-down.
                 */
                PropertyFactory.iconKeepUpright(false),
                /*
                 * icon-offset
                 * Layout property. Optional array of numbers. Defaults to [0,0]. Requires icon-image. Supports interpolateexpressions.
                 * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of icon-size to obtain the final offset in pixels. When combined with icon-rotate the offset will be as if the rotated direction was up.
                 */
                PropertyFactory.iconOffset(new Float[]{0f, 0f}),
                /*
                 *icon-opacity
                 * Paint property. Optional number between 0 and 1 inclusive. Defaults to 1. Requires icon-image. Supports feature-state and interpolateexpressions. Transitionable.
                 * The opacity at which the icon will be drawn.
                 */
                PropertyFactory.iconOpacity(1f),
                /*
                 * icon-optional
                 * Layout property. Optional boolean. Defaults to false. Requires icon-image. Requires text-field.
                 * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
                 */
                PropertyFactory.iconOptional(false),
                /*
                 * icon-padding
                 * Layout property. Optional number greater than or equal to 0. Units in pixels. Defaults to 2. Requires icon-image. Supports interpolateexpressions.
                 * Size of the additional area around the icon bounding box used for detecting symbol collisions.
                 */
                PropertyFactory.iconPadding(0f),
                /*
                 * icon-pitch-alignment
                 * Layout property. Optional enum. One of "map", "viewport", "auto". Defaults to "auto". Requires icon-image.
                 * Orientation of icon when map is pitched.
                 *
                 * "map":
                 * The icon is aligned to the plane of the map.
                 *
                 * "viewport":
                 * The icon is aligned to the plane of the viewport.
                 *
                 * "auto":
                 * Automatically matches the value of icon-rotation-alignment.
                 */
                PropertyFactory.iconPitchAlignment(Property.ICON_PITCH_ALIGNMENT_AUTO),
                /*
                 * icon-rotate
                 * Layout property. Optional number. Units in degrees. Defaults to 0. Requires icon-image. Supports interpolateexpressions.
                 * Rotates the icon clockwise.
                 */
                PropertyFactory.iconRotate(0f),
                /*
                 * icon-rotation-alignment
                 * Layout property. Optional enum. One of "map", "viewport", "auto". Defaults to "auto". Requires icon-image.
                 * In combination with symbol-placement, determines the rotation behavior of icons.
                 *
                 * "map":
                 * When symbol-placement is set to point, aligns icons east-west. When symbol-placement is set to line or line-center, aligns icon x-axes with the line.
                 *
                 * "viewport":
                 * Produces icons whose x-axes are aligned with the x-axis of the viewport, regardless of the value of symbol-placement.
                 *
                 * "auto":
                 * When symbol-placement is set to point, this is equivalent to viewport. When symbol-placement is set to line or line-center, this is equivalent to map.
                 */
                PropertyFactory.iconRotationAlignment(Property.ICON_ROTATION_ALIGNMENT_AUTO),

                /*
                 * icon-size
                 * Layout property. Optional number greater than or equal to 0. Units in factor of the original icon size. Defaults to 1. Requires icon-image. Supports interpolateexpressions.
                 * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by icon-size. 1 is the original size; 3 triples the size of the image.
                 */
                PropertyFactory.iconSize(1f),
                /*
                 * icon-text-fit
                 * Layout property. Optional enum. One of "none", "width", "height", "both". Defaults to "none". Requires icon-image. Requires text-field.
                 * Scales the icon to fit around the associated text.
                 *
                 * "none":
                 * The icon is displayed at its intrinsic aspect ratio.
                 *
                 * "width":
                 * The icon is scaled in the x-dimension to fit the width of the text.
                 *
                 * "height":
                 * The icon is scaled in the y-dimension to fit the height of the text.
                 *
                 * "both":
                 * The icon is scaled in both x- and y-dimensions.
                 */
                PropertyFactory.iconTextFit(Property.ICON_TEXT_FIT_NONE),
                /*
                 *icon-text-fit-padding
                 * Layout property. Optional array of numbers. Units in pixels. Defaults to [0,0,0,0]. Requires icon-image. Requires text-field. Requires icon-text-fit to be "both", or "width", or "height". Supports interpolateexpressions.
                 * Size of the additional area added to dimensions determined by icon-text-fit, in clockwise order: top, right, bottom, left.
                 */
                PropertyFactory.iconTextFitPadding(new Float[]{0f, 0f, 0f, 0f}),
                /*
                 *icon-translate
                 * Paint property. Optional array of numbers. Units in pixels. Defaults to [0,0]. Requires icon-image. Supports interpolateexpressions. Transitionable.
                 * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
                 */
                PropertyFactory.iconTranslate(new Float[]{0f,0f}),
                /*
                 *icon-translate-anchor
                 * Paint property. Optional enum. One of "map", "viewport". Defaults to "map". Requires icon-image. Requires icon-translate.
                 * Controls the frame of reference for icon-translate.
                 *
                 * "map":
                 * Icons are translated relative to the map.
                 *
                 * "viewport":
                 * Icons are translated relative to the viewport.
                 */
                PropertyFactory.iconTranslateAnchor(Property.ICON_TRANSLATE_ANCHOR_MAP),
                /*
                 *symbol-avoid-edges
                 * Layout property. Optional boolean. Defaults to false.
                 * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
                 */
                PropertyFactory.symbolAvoidEdges(false),
                /*
                 *symbol-placement
                 * Layout property. Optional enum. One of "point", "line", "line-center". Defaults to "point".
                 * Label placement relative to its geometry.
                 *
                 * "point":
                 * The label is placed at the point where the geometry is located.
                 *
                 * "line":
                 * The label is placed along the line of the geometry. Can only be used on LineString and Polygon geometries.
                 *
                 * "line-center":
                 * The label is placed at the center of the line of the geometry. Can only be used on LineString and Polygon geometries. Note that a single feature in a vector tile may contain multiple line geometries.
                 */
                PropertyFactory.symbolPlacement(Property.SYMBOL_PLACEMENT_POINT),
                /*
                 * symbol-sort-key
                 * Layout property. Optional number.
                 * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When icon-allow-overlap or text-allow-overlap is false, features with a lower sort key will have priority during placement. When icon-allow-overlap or text-allow-overlap is set to true, features with a higher sort key will overlap over features with a lower sort key.
                 */
//                PropertyFactory.symbolSortKey(),
                /*
                 * symbol-spacing
                 * Layout property. Optional number greater than or equal to 1. Units in pixels. Defaults to 250. Requires symbol-placement to be "line". Supports interpolateexpressions.
                 * Distance between two symbol anchors.
                 */
                PropertyFactory.symbolSpacing(250f),
                /*
                 * symbol-z-order
                 * Layout property. Optional enum. One of "auto", "viewport-y", "source". Defaults to "auto".
                 * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use symbol-sort-key.
                 *
                 * "auto":
                 * Sorts symbols by symbol-sort-key if set. Otherwise, sorts symbols by their y-position relative to the viewport if icon-allow-overlap or text-allow-overlap is set to true or icon-ignore-placement or text-ignore-placement is false.
                 *
                 * "viewport-y":
                 * Sorts symbols by their y-position relative to the viewport if icon-allow-overlap or text-allow-overlap is set to true or icon-ignore-placement or text-ignore-placement is false.
                 *
                 * "source":
                 * Sorts symbols by symbol-sort-key if set. Otherwise, no sorting is applied; symbols are rendered in the same order as the source data.
                 */
                PropertyFactory.symbolZOrder(Property.SYMBOL_Z_ORDER_AUTO),
                /*
                 *text-allow-overlap
                 * Layout property. Optional boolean. Defaults to false. Requires text-field.
                 * If true, the text will be visible even if it collides with other previously drawn symbols.
                 */
                PropertyFactory.textAllowOverlap(false),
                /*
                 *text-anchor
                 * Layout property. Optional enum. One of "center", "left", "right", "top", "bottom", "top-left", "top-right", "bottom-left", "bottom-right". Defaults to "center". Requires text-field. Disabled by text-variable-anchor.
                 * Part of the text placed closest to the anchor.
                 *
                 * "center":
                 * The center of the text is placed closest to the anchor.
                 *
                 * "left":
                 * The left side of the text is placed closest to the anchor.
                 *
                 * "right":
                 * The right side of the text is placed closest to the anchor.
                 *
                 * "top":
                 * The top of the text is placed closest to the anchor.
                 *
                 * "bottom":
                 * The bottom of the text is placed closest to the anchor.
                 *
                 * "top-left":
                 * The top left corner of the text is placed closest to the anchor.
                 *
                 * "top-right":
                 * The top right corner of the text is placed closest to the anchor.
                 *
                 * "bottom-left":
                 * The bottom left corner of the text is placed closest to the anchor.
                 *
                 * "bottom-right":
                 * The bottom right corner of the text is placed closest to the anchor.
                 */
                PropertyFactory.textAnchor(Property.TEXT_ANCHOR_CENTER),
                /*
                 *text-color
                 * Paint property. Optional color. Defaults to "#000000". Requires text-field. Supports feature-state and interpolateexpressions. Transitionable.
                 * The color with which the text will be drawn.
                 */
                PropertyFactory.textColor(Color.parseColor("#000000")),
                /*
                 *text-field
                 * Layout property. Optional formatted. Defaults to "".
                 * Value to use for a text label. If a plain string is provided, it will be treated as a formatted with default/inherited formatting options.
                 */
                PropertyFactory.textField(""),
                /*
                 *text-font
                 * Layout property. Optional array of strings. Defaults to ["Open Sans Regular","Arial Unicode MS Regular"]. Requires text-field.
                 * Font stack to use for displaying text.
                 */
                PropertyFactory.textFont(new String[]{"Open Sans Regular","Arial Unicode MS Regular"}),
                /*
                 *text-halo-blur
                 * Paint property. Optional number greater than or equal to 0. Units in pixels. Defaults to 0. Requires text-field. Supports feature-state and interpolateexpressions. Transitionable.
                 * The halo's fadeout distance towards the outside.
                 */
                PropertyFactory.textHaloBlur(0f),
                /*
                 *text-halo-color
                 * Paint property. Optional color. Defaults to "rgba(0, 0, 0, 0)". Requires text-field. Supports feature-state and interpolateexpressions. Transitionable.
                 * The color of the text's halo, which helps it stand out from backgrounds.
                 */
                PropertyFactory.textHaloColor(Color.argb(0, 0, 0, 0)),
                /*
                 *text-halo-width
                 * Paint property. Optional number greater than or equal to 0. Units in pixels. Defaults to 0. Requires text-field. Supports feature-state and interpolateexpressions. Transitionable.
                 * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
                 */
                PropertyFactory.textHaloWidth(0f),
                /*
                 *text-ignore-placement
                 * Layout property. Optional boolean. Defaults to false. Requires text-field.
                 * If true, other symbols can be visible even if they collide with the text.
                 */
                PropertyFactory.textIgnorePlacement(false),
                /*
                 *text-justify
                 * Layout property. Optional enum. One of "auto", "left", "center", "right". Defaults to "center". Requires text-field.
                 * Text justification options.
                 *
                 * "auto":
                 * The text is aligned towards the anchor position.
                 *
                 * "left":
                 * The text is aligned to the left.
                 *
                 * "center":
                 * The text is centered.
                 *
                 * "right":
                 * The text is aligned to the right.
                 */
                PropertyFactory.textJustify(Property.TEXT_JUSTIFY_CENTER),
                /*
                 *text-keep-upright
                 * Layout property. Optional boolean. Defaults to true. Requires text-field. Requires text-rotation-alignment to be "map". Requires symbol-placement to be "line", or "line-center".
                 * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
                 */
                PropertyFactory.textKeepUpright(true),
                /*
                 *text-letter-spacing
                 * Layout property. Optional number. Units in ems. Defaults to 0. Requires text-field. Supports interpolateexpressions.
                 * Text tracking amount.
                 */
                PropertyFactory.textLetterSpacing(0f),
                /*
                 *text-line-height
                 * Layout property. Optional number. Units in ems. Defaults to 1.2. Requires text-field. Supports interpolateexpressions.
                 * Text leading value for multi-line text.
                 */
                PropertyFactory.textLineHeight(1.2f),
                /*
                 *text-max-angle
                 * Layout property. Optional number. Units in degrees. Defaults to 45. Requires text-field. Requires symbol-placement to be "line", or "line-center". Supports interpolateexpressions.
                 * Maximum angle change between adjacent characters.
                 */
                PropertyFactory.textMaxAngle(45f),
                /*
                 *text-max-width
                 * Layout property. Optional number greater than or equal to 0. Units in ems. Defaults to 10. Requires text-field. Supports interpolateexpressions.
                 * The maximum line width for text wrapping.
                 */
                PropertyFactory.textMaxWidth(10f),
                /*
                 *text-offset
                 * Layout property. Optional array of numbers. Units in ems. Defaults to [0,0]. Requires text-field. Disabled by text-radial-offset. Supports interpolateexpressions.
                 * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
                 */
                PropertyFactory.textOffset(new Float[]{0f,0f}),
                /*
                 *text-opacity
                 * Paint property. Optional number between 0 and 1 inclusive. Defaults to 1. Requires text-field. Supports feature-state and interpolateexpressions. Transitionable.
                 * The opacity at which the text will be drawn.
                 */
                PropertyFactory.textOpacity(1f),
                /*
                 *text-optional
                 * Layout property. Optional boolean. Defaults to false. Requires text-field. Requires icon-image.
                 * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
                 */
                PropertyFactory.textOptional(false),
                /*
                 *text-padding
                 * Layout property. Optional number greater than or equal to 0. Units in pixels. Defaults to 2. Requires text-field. Supports interpolateexpressions.
                 * Size of the additional area around the text bounding box used for detecting symbol collisions.
                 */
                PropertyFactory.textPadding(0f),
                /*
                 *text-pitch-alignment
                 * Layout property. Optional enum. One of "map", "viewport", "auto". Defaults to "auto". Requires text-field.
                 * Orientation of text when map is pitched.
                 *
                 * "map":
                 * The text is aligned to the plane of the map.
                 *
                 * "viewport":
                 * The text is aligned to the plane of the viewport.
                 *
                 * "auto":
                 * Automatically matches the value of text-rotation-alignment.
                 */
                PropertyFactory.textPitchAlignment(Property.TEXT_PITCH_ALIGNMENT_AUTO),
                /*
                 * text-radial-offset
                 * Layout property. Optional number. Units in ems. Defaults to 0. Requires text-field. Supports interpolateexpressions.
                 * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with text-variable-anchor, which defaults to using the two-dimensional text-offset if present.
                 */
                PropertyFactory.textRadialOffset(0f),
                /*
                 *text-rotate
                 * Layout property. Optional number. Units in degrees. Defaults to 0. Requires text-field. Supports interpolateexpressions.
                 * Rotates the text clockwise.
                 */
                PropertyFactory.textRotate(0f),
                /*
                 *text-rotation-alignment
                 * Layout property. Optional enum. One of "map", "viewport", "auto". Defaults to "auto". Requires text-field.
                 * In combination with symbol-placement, determines the rotation behavior of the individual glyphs forming the text.
                 *
                 * "map":
                 * When symbol-placement is set to point, aligns text east-west. When symbol-placement is set to line or line-center, aligns text x-axes with the line.
                 *
                 * "viewport":
                 * Produces glyphs whose x-axes are aligned with the x-axis of the viewport, regardless of the value of symbol-placement.
                 *
                 * "auto":
                 * When symbol-placement is set to point, this is equivalent to viewport. When symbol-placement is set to line or line-center, this is equivalent to map.
                 */
                PropertyFactory.textRotationAlignment(Property.TEXT_ROTATION_ALIGNMENT_AUTO),
                /*
                 *text-size
                 * Layout property. Optional number greater than or equal to 0. Units in pixels. Defaults to 16. Requires text-field. Supports interpolateexpressions.
                 * Font size.
                 */
                PropertyFactory.textSize(16f),
                /*
                 *text-transform
                 * Layout property. Optional enum. One of "none", "uppercase", "lowercase". Defaults to "none". Requires text-field.
                 * Specifies how to capitalize text, similar to the CSS text-transform property.
                 *
                 * "none":
                 * The text is not altered.
                 *
                 * "uppercase":
                 * Forces all letters to be displayed in uppercase.
                 *
                 * "lowercase":
                 * Forces all letters to be displayed in lowercase.
                 */
                PropertyFactory.textTransform(Property.TEXT_TRANSFORM_NONE),
                /*
                 *text-translate
                 * Paint property. Optional array of numbers. Units in pixels. Defaults to [0,0]. Requires text-field. Supports interpolateexpressions. Transitionable.
                 * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
                 */
                PropertyFactory.textTranslate(new Float[]{0f, 0f}),
                /*
                 *text-translate-anchor
                 * Paint property. Optional enum. One of "map", "viewport". Defaults to "map". Requires text-field. Requires text-translate.
                 * Controls the frame of reference for text-translate.
                 *
                 * "map":
                 * The text is translated relative to the map.
                 *
                 * "viewport":
                 * The text is translated relative to the viewport.
                 */
                PropertyFactory.textTranslateAnchor(Property.TEXT_TRANSLATE_ANCHOR_MAP),
                /*
                 *text-variable-anchor
                 * Layout property. Optional array of enums. One of "center", "left", "right", "top", "bottom", "top-left", "top-right", "bottom-left", "bottom-right". Requires text-field. Requires symbol-placement to be "point".
                 * To increase the chance of placing high-priority labels on the map, you can provide an array of text-anchor locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use text-justify: auto to choose justification based on anchor position. To apply an offset, use the text-radial-offset or the two-dimensional text-offset.
                 *
                 * "center":
                 * The center of the text is placed closest to the anchor.
                 *
                 * "left":
                 * The left side of the text is placed closest to the anchor.
                 *
                 * "right":
                 * The right side of the text is placed closest to the anchor.
                 *
                 * "top":
                 * The top of the text is placed closest to the anchor.
                 *
                 * "bottom":
                 * The bottom of the text is placed closest to the anchor.
                 *
                 * "top-left":
                 * The top left corner of the text is placed closest to the anchor.
                 *
                 * "top-right":
                 * The top right corner of the text is placed closest to the anchor.
                 *
                 * "bottom-left":
                 * The bottom left corner of the text is placed closest to the anchor.
                 *
                 * "bottom-right":
                 * The bottom right corner of the text is placed closest to the anchor.
                 */
                PropertyFactory.textVariableAnchor(new String[]{"center"}),
                /*
                 *text-writing-mode
                 * Layout property. Optional array of enums. One of "horizontal", "vertical". Requires text-field. Requires symbol-placement to be "point".
                 * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. The order of elements in an array define priority order for the placement of an orientation variant.
                 *
                 * "horizontal":
                 * If a text's language supports horizontal writing mode, symbols with point placement would be laid out horizontally.
                 *
                 * "vertical":
                 * If a text's language supports vertical writing mode, symbols with point placement would be laid out vertically.
                 */
                PropertyFactory.textWritingMode(new String[]{Property.TEXT_WRITING_MODE_HORIZONTAL}),
                /*
                 *visibility
                 * Layout property. Optional enum. One of "visible", "none". Defaults to "visible".
                 * Whether this layer is displayed.
                 *
                 * "visible":
                 * The layer is shown.
                 *
                 * "none":
                 * The layer is not shown.
                 */
                PropertyFactory.visibility(Property.VISIBLE)
                );
        //更新位置
        feature = Feature.fromGeometry(Point.fromLngLat(-77.03,38.875));
        featureCollection = FeatureCollection.fromFeatures(new Feature[]{feature});
        geoJsonSource.setGeoJson(featureCollection);
    }

    //region 生命周期
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animatorSize != null) {
            animatorSize.cancel();
        }
        if (animatorPosition != null) {
            animatorPosition.cancel();
        }
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    //endregion
}