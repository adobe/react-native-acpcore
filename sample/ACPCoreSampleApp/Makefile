sync:
	(cd ../../ && rm adobe-react-native-acpcore-2.0.1.tgz || true)
	(cd ../../ && npm pack)
	(rm -r node_modules/@adobe/react-native-acpcore || true)
	(rm package-lock.json || true)
	(npm cache clean --force && npm install)