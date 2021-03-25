package com.example.dev_mob_houet_piron.model;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class LocatorUtil {

    /**
     * Retourne la position de l'utilisateur sous forme d'objet LatLng
     * @param context Objet du type contexte nécéssaire à la méthode pour pouvoir effectuer ses requêtes
     * @return Un objet du type LatLng correspondant à la localisation de l'utilisateur
     */
    public static LatLng getCurrentLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("LocatorUtil", "Unable to get user position: permission denied");
            return new LatLng(0, 0);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return (location == null)? new LatLng(0, 0) : new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static boolean UserIsAtRange(Context context, LatLng coord, int range) {
        return SphericalUtil.computeDistanceBetween(getCurrentLocation(context), coord) <= range;
    }
}
