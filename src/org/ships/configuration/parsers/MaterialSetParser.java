package org.ships.configuration.parsers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.Tag;

public class MaterialSetParser implements Parser<List<Material>, List<String>> {
	@SuppressWarnings("deprecation")
	@Override
	public Optional<List<Material>> parse(List<String> parse) {
		ArrayList<Material> set = new ArrayList<Material>();
		for (String value : parse) {
			for (Map.Entry<String, Tag<Material>> entry : this.getTags().entrySet()) {
				if (!entry.getKey().equalsIgnoreCase(value))
					continue;
				set.addAll((entry.getValue()).getValues());
			}
			for (Material material : Material.values()) {
				if (!material.name().equalsIgnoreCase(value) || material.isLegacy())
					continue;
				set.add(material);
			}
		}
		return Optional.of(set);
	}

	@Override
	public List<String> toString(List<Material> string) {
		ArrayList<String> set = new ArrayList<String>();
		string.stream().forEach(s -> {
			set.add(s.name());
		});
		return set;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Tag<Material>> getTags() {
		HashMap<String, Tag<Material>> set = new HashMap<String, Tag<Material>>();
		for (Field field : Tag.class.getFields()) {
			try {
				Object object = field.get(null);
				if (object instanceof String)
					continue;
				set.put(field.getName(), (Tag<Material>)object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return set;
	}
}
