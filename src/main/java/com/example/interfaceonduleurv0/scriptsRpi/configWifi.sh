sudo cp /etc/wpa_supplicant/wpa_supplicant_original.conf /etc/wpa_supplicant/wpa_supplicant.conf;
sudo wpa_passphrase $1 $2 >> /etc/wpa_supplicant/wpa_supplicant.conf;
sudo wpa_cli -i wlan0 reconfigure;